package br.com.segurosunimed.gestaocarteiras.commons.controller.exception;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.segurosunimed.gestaocarteiras.commons.dto.LogDTO;
import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEventType;
import br.com.segurosunimed.gestaocarteiras.commons.enums.GcLogType;
import br.com.segurosunimed.gestaocarteiras.commons.exception.BusinessException;
import br.com.segurosunimed.gestaocarteiras.commons.exception.CustomInternalServerErrorException;
import br.com.segurosunimed.gestaocarteiras.commons.exception.ForbiddenException;
import br.com.segurosunimed.gestaocarteiras.commons.exception.UnauthorizedException;
import br.com.segurosunimed.gestaocarteiras.commons.service.history.LogServiceSender;
import br.com.segurosunimed.gestaocarteiras.commons.util.GcGeralUtil;


@RestControllerAdvice
public class ExceptionHandlerController {

    static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LogServiceSender logServiceSender;

	@Autowired
	GcGeralUtil geralUtil;
	
    @Value("${gc.queue-log}")
    private String queueLog;

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public List<ErrorDTO> unauthorized(UnauthorizedException ex) {

        List<ErrorDTO> errors = new ArrayList<>();
        for (ErrorDTO e : ex.getErrors()) {
            String message = getMessageI18n(e);
            errors.add(new ErrorDTO(e.getErrorId(), e.getErrorType(), message));
        }
        return errors;
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public List<ErrorDTO> forbidden(ForbiddenException ex) {

        List<ErrorDTO> errors = new ArrayList<>();
        for (ErrorDTO e : ex.getErrors()) {
            String message = getMessageI18n(e);
            errors.add(new ErrorDTO(e.getErrorId(), e.getErrorType(), message));
        }
        return errors;
    }

	private String getMessageI18n(ErrorDTO errorDTO) {
		return getMessageI18n(errorDTO.getMessage());
	}

	private String getMessageI18n(String message) {
		String i18nMessage = message;
		try {
			i18nMessage = messageSource.getMessage(message, new Object[]{}, Locale.getDefault());			
		} catch (NoSuchMessageException e) {
			//LOGGER.warn("Erro ao recuperar mensagem: nenhum valor encontrada para mensagem '"+message+"'");
		}
		return i18nMessage;
	}

    @ExceptionHandler(value = {CustomInternalServerErrorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<ErrorDTO> customInternalServerError(CustomInternalServerErrorException ex) {

        List<ErrorDTO> errors = new ArrayList<>();
        for (ErrorDTO e : ex.getErrors()) {
            String message = getMessageI18n(e);
            errors.add(new ErrorDTO(e.getErrorId(), e.getErrorType(), message));
        }
        return errors;
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public List<ErrorDTO> accessDeniedException(AccessDeniedException ex) {
        List<ErrorDTO> errors = new ArrayList<>();
        String message = getMessageI18n("access.denied");
        errors.add(new ErrorDTO("access_denied", message));
        return errors;
    }

    @ExceptionHandler(value = {BusinessException.class})
    public List<ErrorDTO> businessException(BusinessException ex) {
        List<ErrorDTO> errors = handleException(ex);
        return errors;
    }
    
    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public List<ErrorDTO> emptyResultDataAccessException(EmptyResultDataAccessException ex) {
        
    	String[] msgGrp = geralUtil.getGruposCombinacaoExpressao(ex.getMessage(), ".*\\.(.*)(?:Entity entity with id )(\\d*)", new int[] {1,2});
        ErrorDTO errorDTO = new ErrorDTO("id.not.valid", msgGrp[0]+" inexistente para ID "+msgGrp[1] );
        List<ErrorDTO> errors = handleException(new BusinessException(errorDTO));
        return errors;
    }

    @ExceptionHandler(value = {JpaSystemException.class})
    public List<ErrorDTO> emptyResultDataAccessException(JpaSystemException ex) {
        
    	String[] msgGrp = geralUtil.getGruposCombinacaoExpressao(ex.getMessage(), ".*\\.(.*)(?:Entity was altered from )(\\d*) to (\\d*)", new int[] {1,2,3});
        ErrorDTO errorDTO = new ErrorDTO("id.not.valid", "Substituicao incorreta do ID de "+msgGrp[0]+" de "+msgGrp[1]+ " para "+ msgGrp[2]);
        List<ErrorDTO> errors = handleException(new BusinessException(errorDTO));
        return errors;
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<ErrorDTO> internalServerError(Exception ex) {

    	List<ErrorDTO> errors = handleException(ex);

        return errors;
    }

	private List<ErrorDTO> handleException(Exception ex) {
		
		List<ErrorDTO> errors = new ArrayList<>();
		String messageSource = "";

		//TRATAMENTO EXCEÇÕES BUSINESSEXCEPTION
        if(ex.getClass().isAssignableFrom(BusinessException.class)) {

        	BusinessException businessEx = (BusinessException) ex;

        	//TRATAMENTO EXCEÇÕES DISPARADAS PARA BUSINESSEXCEPTION
        	if(businessEx.getErrors() != null && !businessEx.getErrors().isEmpty()) {
        		
        		List<ErrorDTO> errorsDTO = businessEx.getErrors();
        		
        		for (ErrorDTO error : errorsDTO) {
        			
                	//TRATAMENTO EXCEÇÕES DISPARADAS PARA ERROS ESPECÍFICOS
        			if(error.getException() != null) {
        				
        				error.getException().printStackTrace();
        				
        				try {
        					String fkName = "";
							messageSource = "";
							
							//TRATAMENTO EXCEÇÕES DE BANCO DE DADOS PARA VIOLAÇÃO DE CHAVES ESTRANGEIRAS 
        					if(error.getException().getCause() != null && 
        							error.getException().getCause().getClass().isAssignableFrom(ConstraintViolationException.class)) {
        						ConstraintViolationException cvEX = (ConstraintViolationException) error.getException().getCause();
        						if(cvEX != null && cvEX.getSQLException() != null) {
        							SQLException sqlEx = cvEX.getSQLException();
        							if(sqlEx != null) {
        								fkName = geralUtil.getCombinacaoExpressao(sqlEx.getMessage(), "\"(FK.*?)\"", new int[]{1});
            							if(StringUtils.isBlank(fkName)) {
            								fkName = geralUtil.getCombinacaoExpressao(sqlEx.getMessage(), "(NULL)", new int[]{1});
            								if(StringUtils.isNotBlank(fkName)) {
//                								fkName = geralUtil.getCombinacaoExpressao(sqlEx.getMessage(), "(NULL).*\\s(\\'.*\\'),", new int[]{2});
                								messageSource = getMessageI18n(sqlEx.getMessage());
            								}else {
            									messageSource = getMessageI18n("entity.save.error");
            								}
            							}else {
            								//Erro integridade
            								if(StringUtils.contains(sqlEx.getMessage(), "DELETE") && StringUtils.contains(sqlEx.getMessage(), "REFERENCE")) {
                								messageSource = getMessageI18n(fkName + "." +"entity.delete.error");
            								}else {
            									messageSource = getMessageI18n(fkName + "." +"entity.save.error");
            								}
            							}
        								error = new ErrorDTO("database.fkintegrity.service.error", messageSource);
        							}
        						}
        					}
        					
        				} catch (Exception e) {
							//ERRO NO FLUXO DE TRATAMENTO EXCEÇÕES 
        					error = new ErrorDTO("internal.server.error", getMessageI18n("unspected.server.error"));
        					e.printStackTrace();
        				}
        				error.setException(null);
        			}else {
//    					error.setErrorType(getMessageI18n(error.getErrorType()));
    					error.setMessage(getMessageI18n(error.getMessage()));
    					LOGGER.error(error.getMessage());
    			    	ex.printStackTrace();
        			}
        	        errors.add(error);
        	        LogDTO logDTO = getLogDTO(businessEx, error);
        	        logServiceSender.sendLog(logDTO);
        	        
				}
        	}
        }else {
			//TRATAMENTO EXCEÇÕES INESPERADAS SEM MENSAGENS ESPECÍFICAS
            ErrorDTO errorDTO = new ErrorDTO("internal.server.error", getMessageI18n("unspected.server.error"));
            errors.add(errorDTO);
	        LogDTO logDTO = getLogDTO(ex, errorDTO);
	        logServiceSender.sendLog(logDTO);
        	ex.printStackTrace();
        	errors.add(errorDTO);
        	throw new RuntimeException(ex);
//        	return errors;
        }
        
		return errors;
	}

    private LogDTO getLogDTO(Exception ex, ErrorDTO errorDTO) {
        LogDTO logDTO = LogDTO.builder().
                level(GcLogType.ERROR).
                eventType(GcEventType.GENERIC_ERROR).
                errorId(errorDTO.getErrorId()).
                errorType(GcEventType.GENERIC_ERROR.name()).
                message(String.format("Exception\n%s", ex.getCause())).
                extraData(String.format("Stack\n%s", ex)).
                build();
        return logDTO;
    }
}

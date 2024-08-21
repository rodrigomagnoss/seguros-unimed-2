package br.com.segurosunimed.gestaocarteiras.commons.exception;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gc - Classe helper para auxilio no envio de excecoes tratadas
 *
 *@author Rodrigo Magno
  * @since 19/06/2024
 */
public class GcExceptionHelper {
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("sistema");

    private static GcExceptionHelper INSTANCE = new GcExceptionHelper();
    private GcExceptionHelper(){}
     
    
    public static GcExceptionHelper getInstance() {
        return INSTANCE;
    }
    
	/**
	 * Envia uma mensagem com nivel ERROR a partir da excecao retornando uma excecao do tipo Exception.
	 * 
	 * @param e Excecao capturada
	 * 
	 * @author Rodrigo Magno
	  * @since 22/12/2010 
	 */
	public Exception dispararErrorException(Logger logger, String erroMsg, Exception e){

//		EcpAppenderController ecpAppender = EcpAppenderController.getInstance();
		
		erroMsg = StringUtils.replace(erroMsg, "###", "");
		erroMsg = StringUtils.replace(erroMsg, "##", "");
		erroMsg = StringUtils.replace(erroMsg, "#", "");
		if(erroMsg.contains("LOG:")){
			erroMsg = (StringUtils.substringAfter(erroMsg, ")").trim());
		}
		
        Calendar calendar 	= Calendar.getInstance();
        Long tempoAgora		= calendar.getTimeInMillis();
		String msgLog = erroMsg;
        Long maxLog	= 10L;
		Long numLog = 0L;
		Long tempoLog = 10L;
		
		return e;
		
//        Object[] objMsg = EcpMensagensLogger.getErros().get(erroMsg);
//        if(objMsg != null){
//    		msgLog = (String) objMsg[0];
//            maxLog	= (Long) objMsg[1];
//            tempoLog	= (Long) objMsg[2];
//        }
		
//        boolean enviarParaConsole = true;
//        Object[] objLog = null;
//		
//		if(msgLog != null){
//			objLog = ecpAppender.getAssuntosBloqueados().get(msgLog);
//			if(objLog != null){
//				numLog = (Long) objLog[0];
//				if(numLog >= maxLog){
//					if(ecpAppender.podeMostrarMensagem(objLog)){
//						objLog[0] = 0L;
//					}else{
//						enviarParaConsole = false;
//					}
//				}
//				objLog[0] = ++numLog;
//			}else{
//				
//				StackTraceElement[] stacks = e.getStackTrace();
//				String firstLineStack = "";
//				if(stacks != null && stacks.length > 0){
//					firstLineStack = ""+stacks[0];
//				}
//				
//				objLog = new Object[]{numLog, maxLog, tempoLog, tempoAgora,ecpAppender.getAssuntosBloqueados().size() + 1, firstLineStack};
//				HashMap mapLog = new HashMap();
//				mapLog.put(msgLog, objLog);
//				ecpAppender.addAssuntosBloqueados(mapLog);
//			}
//		}

//		erroMsg = dispararLogException(logger, erroMsg, e, enviarParaConsole);
//		
//		PlcException plcEx = criarPlcException("#"+erroMsg, e);
		
//		return plcEx;
	}

	/**
	 * Dispara um log de excecao 
	 * @param logger Instancia do log
	 * @param erroMsg Mensagem de erro
	 * @param exception Excecao tratada
	 * @return String de erro
	 */
	private String dispararLogException(Logger logger, String erroMsg, Exception exception) {
		return dispararLogException(logger, erroMsg, exception, true);
	}

	/**
	 * Dispara um log de excecao 
	 * @param logger Instancia do log
	 * @param erroMsg Mensagem de erro
	 * @param exception Excecao tratada
	 * @param enviarParaConsole Define se envia o log para console
	 * @return String de erro
	 */
	private String dispararLogException(Logger logger, String erroMsg, Exception exception, boolean enviarParaConsole) {
		
		String exMsg = StringUtils.isNotBlank(erroMsg) ? erroMsg : "";

		String erroMsgBase = "";
		if(!StringUtils.contains(erroMsg, getMsgExcecaoSistema())){
			erroMsgBase = erroMsg + getMsgExcecaoSistema() + exMsg.replace("#", ""); 
		}else{
			erroMsgBase = erroMsg;
		}

		if(enviarParaConsole){
			//String erroMsgLog = erroMsg + getMsgExcecaoSistema() + exMsg.replace("#", "");
			String erroMsgLog  = erroMsgBase;
			erroMsgLog = erroMsgLog.replace("<BR>", " ").replace("<br>", " ");
			erroMsgLog = StringUtils.trim(erroMsgLog);
			if(exception instanceof Exception){
				Exception ex = (Exception) exception; 
//				if(!plcEx.isJaEmitiuLogging()){
//					plcEx.setJaEmitiuLogging(true);
//					GcLoggerService.getInstance().dispararLogErro(logger, erroMsgLog, e);	
//				}else{
//					GcLoggerService.getInstance().dispararLogErro(logger, erroMsgLog, new GcNoStackTraceException(e));
//				}
			}else{
				//GcLoggerService.getInstance().dispararLogErro(logger, erroMsgLog, e);				
			}

		}
		
		//erroMsg = erroMsg + getMsgExcecaoSistema() + exMsg.replace("#", ""); 
		erroMsg = erroMsgBase; 
//		erroMsg = GcLoggerService.getInstance().adicionarProfileLogMensagem(erroMsg);
		erroMsg = erroMsg.replace(getMsgExcecaoSistema(), "<br>"+getMsgExcecaoSistema());
		return erroMsg;
	}
	
	/**
	 * Cria uma excecao do tipo Exception
	 * @param erroMsg Mensagem de erro
	 * @param exception Excecao a ser encapsulada
	 * @return Nova instancia de Exception
	 * 
	 * @author Rodrigo Magno
	  * @since 07/05/2014
	 */
	private Exception criarException(String erroMsg, Exception exception) {
		if(exception != null){
			return new Exception(erroMsg, exception);
		}else{
			return new Exception(erroMsg);
		}
	}

	/**
	 * Retorna token anexo para mensagem sistema
	 * 
	 * @author Rodrigo Magno
	  * @since 22/12/2010 
	 */
	protected String getMsgExcecaoSistema() {
		return " Excecao do sistema: ";
	}
	
	/**
	 * Envia uma mensagem de log conforme o nivel especificado
	 * @param e Excecao capturada
	 * 
	 * @author Rodrigo Magno
	  * @since 22/12/2010
	 */
	public void dispararLog(Logger logger, String mensagem, String nivel, Exception e) throws Exception {

//		mensagem = mensagem + getMsgExcecaoSistema() + e;
//		loggerServ.dispararLog(logger, mensagem, nivel, e);
	}
	
	/**
	 * Verifica se a excecao passada ou alguma de suas causas raizes e uma GcNoStackTraceException
	 * 
	 * @author Rodrigo Magno
	  * @since 24/01/2014
	 */
	public GcNoStackTraceException isNoStackTraceException(Throwable exception){

		Exception exPlc 	= exception != null && Exception.class.isAssignableFrom(exception.getClass()) ? (Exception) exception : null;
//		
		boolean isExcecaoNoStack = exPlc != null ? GcNoStackTraceException.class.isAssignableFrom(exPlc.getClass()) : false;
//		
//		int cont = 10;
//		if(exPlc != null){
//			while(exPlc != null && !isExcecaoNoStack && cont > 0){
//				exPlc 	= exPlc.getCausaRaiz() != null && PlcException.class.isAssignableFrom(exPlc.getCausaRaiz().getClass()) ? (PlcException) exPlc.getCausaRaiz() : null;
//				isExcecaoNoStack = exPlc != null ? GcNoStackTraceException.class.isAssignableFrom(exPlc.getClass()) : false;
//				cont--;
//			};
//			exception = exPlc;
//		}else if (exception != null ){
//			while(exception != null && !isExcecaoNoStack && cont > 0){
//				exception 	= exception.getCause() != null ? exception.getCause() : null;
//				isExcecaoNoStack = exception != null ? GcNoStackTraceException.class.isAssignableFrom(exception.getClass()) : false;
//				cont--;
//			};
//		}
//		
		if(isExcecaoNoStack){
		//	return (GcNoStackTraceException) exception;
			return null;
		}else{
			return null;
		}
	}
}

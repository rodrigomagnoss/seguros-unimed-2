package br.com.segurosunimed.gestaocarteiras.commons.service.history;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.segurosunimed.gestaocarteiras.commons.dto.BaseDTO;
import br.com.segurosunimed.gestaocarteiras.commons.dto.LogDTO;
import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEventMessage;
import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEventType;
import br.com.segurosunimed.gestaocarteiras.commons.enums.GcLogType;

@Service
public class HistoryLogServiceSender extends LogServiceSender {

    static final Logger LOGGER = LoggerFactory.getLogger(HistoryLogServiceSender.class);
	
    public void sendLogCreate(Object entity) {

    	BaseDTO baseDTO = (BaseDTO) entity;
    	
        try {
        	
            LogDTO logDTO = LogDTO.builder()
                    .level(GcLogType.INFO)
                    .eventType(GcEventType.CREATE_ENTITY)
                    .message(GcEventMessage.CREATE_ENTITY.value().replace("#ENTITY#", getEntityName(baseDTO)))
                    .extraData(
                    		mapper.writeValueAsString(baseDTO)
                    		)
                    .eventDate(new Date())
                    .build();
            sendLog(logDTO);
            
        } catch (JsonProcessingException e) {
            LOGGER.error("Erro ao enviar historico para log create", e);
        }
    }

    public void sendLogUpdate(Object entity) {

    	BaseDTO baseDTO = (BaseDTO) entity;

    	try {
        	
            LogDTO logDTO = LogDTO.builder()
                    .level(GcLogType.INFO)
                    .eventType(GcEventType.UPDATE_ENTITY)
                    .message(GcEventMessage.UPDATE_ENTITY.value().replace("#ENTITY#", getEntityName(baseDTO)))
                    .extraData(
                    		mapper.writeValueAsString(baseDTO)
                    		)
                    .eventDate(new Date())
                    .build();
            sendLog(logDTO);
            
        } catch (JsonProcessingException e) {
            LOGGER.error("Erro ao enviar historico para log update", e);
        }
    }

	private String getEntityName(BaseDTO baseDTO) {
//		String entityName = baseDTO.getClass().getAnnotation(Qualifier.class) != null ? baseDTO.getClass().getAnnotation(Qualifier.class).value() : 
//    						baseDTO.getClass().getSuperclass().getAnnotation(Qualifier.class) != null ? baseDTO.getClass().getSuperclass().getAnnotation(Qualifier.class).value() : 
//    							StringUtils.capitalize(StringUtils.substringAfterLast(baseDTO.getClass().getPackageName(), "."));
//		
//		return entityName;
		
		return "";
	}


}

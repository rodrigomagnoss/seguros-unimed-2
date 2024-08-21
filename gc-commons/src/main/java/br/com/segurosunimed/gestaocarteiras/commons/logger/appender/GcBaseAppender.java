package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import br.com.segurosunimed.gestaocarteiras.commons.constants.GcConstantes;
import br.com.segurosunimed.gestaocarteiras.commons.exception.GcNoLoggingException;
import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcLoggingTag;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcHistMsgLog;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcMailMessage;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcMailMessageFacade;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcMailMessageLogging;

/**
 * Gc. Classe base para configuração de appenders para envio de emails
 * 
 * @author Rodrigo Magno
 */
public class GcBaseAppender extends AppenderSkeleton {

	protected Logger logL = Logger.getLogger("logging");

    Map mapAppenders = new HashMap();
    
    /**
     * Verifica o tipo de mensagem  e cria uma instância de evento de log
     */
    public LoggingEvent traduzLoggingEvent(LoggingEvent evt) {
        
    	Object obj = evt.getMessage();
    	if (obj instanceof GcMailMessage && 
    		!obj.getClass().isAssignableFrom(GcHistMsgLog.class)) {
    		return evt;
    	} else { 
    		GcMailMessage msg = null;
    		int level = evt.getLevel().toInt();
			GcMailMessageLogging msgLog = new GcMailMessageLogging();
    		
    		if ((obj instanceof String || obj.getClass().isAssignableFrom(GcHistMsgLog.class)) && 
    				(level == Level.INFO_INT || level == Level.WARN_INT || level == Level.ERROR_INT || level == Level.FATAL_INT)) {
    			msg = new GcMailMessage();
        		if (obj instanceof String) {
	    			msgLog.setBody((String) obj);
        		} else if (obj.getClass().isAssignableFrom(GcHistMsgLog.class)) {
        			msgLog = (GcHistMsgLog) obj;
	    			msgLog.setBody((String) msgLog.getMensagem());
	    			//msgLog.setTipoObjeto(msgHist.getTipo());
        		}
    			msgLog.setLevel(evt.getLevel().toString());
    			msg.setMailMessage(msgLog);

    		} 
    		else if (obj instanceof GcMailMessage) {
    			if (obj.getClass().isAssignableFrom(GcHistMsgLog.class)) {
        			msgLog = (GcHistMsgLog) obj;
        			msg = new GcMailMessage();
        			msg.setMailMessage(msgLog);
    			}else{
        			msg = (GcMailMessage) obj;
    			}
    		}

    		if (msg != null) {
    			Throwable throwable = null;
    			if (evt.getThrowableInformation() != null) {
    				throwable = evt.getThrowableInformation().getThrowable();
    			}
    			return new LoggingEvent(
    					evt.fqnOfCategoryClass, 
    					Category.getInstance(evt.getLoggerName()), 
    					evt.timeStamp,
    					evt.level, 
    					msg, 
    					throwable);
    		} else {
    			return evt;
    		}
    	}
    }
    
    /**
     * Override do m�todo append para seleção do melhor appender para envio de
     * mensagens de email.
     * <p>
     * Primeiro, desvia o processamento do evento para o appender de email
     * local, se estiver configurado. Em segundo, se houver appender 
     * configurado, desvia o processamento para este, para que outras
     * informações sejam enviadas ao Monitor.
     * </p>
     * Ou envio mensagem de log normalmente
     * 
     */
    @SuppressWarnings("deprecation")
	protected void append(LoggingEvent evt) {
        
//    	if(GcConstantes.TP_MSG_LOG.equals(evt.categoryName)){
//			ThrowableInformation err = evt.getThrowableInformation();
//	        Throwable th = evt.getThrowableInformation() != null ? evt.getThrowableInformation().getThrowable() : null;
//	        if(th != null && GcNoLoggingException.class.isAssignableFrom(th.getClass())){
//	    		return;
//	        }
//    	}
    	
    	evt = traduzLoggingEvent(evt);

    	Object logMsg = evt.getMessage();

    	if (logMsg instanceof GcMailMessage) {

    		GcMailMessage mailMessage = (GcMailMessage) logMsg;

    		processMessage(evt, mailMessage);

    		boolean processou = false;
    		GcMailAppender appenderMail = (GcMailAppender) mapAppenders.get(GcConstantes.LOGGER.APPENDER.NOME);
    		appenderMail.setEnviadoLog(false);
    		appenderMail.setEnviadoMail(false);

    		//Tenta o envio com appender de email local.
    		if (appenderMail != null) {
    			//mailMessage.setModoExecucao(appenderMail.getModoExecucao());
       			appenderMail.doAppend(evt);
    			processou = true;
    		}

    		if (!processou) {
    			logL.warn("Email de log nao enviado porque nao ha appender para email configurado", new GcNoLoggingException());
    		}
    	}
    }

    /**
     * Registra objetos appenders utilizados para envio de email
     * @param appenders Array de appenders para registro
     * @return Map com appenders registrados
     */
    public Map registraLogAppenders(AppenderSkeleton[] appenders ) {

		logL.info(new GcHistMsgLog("Registrando appenders de interceptacao de log"));

		AppenderSkeleton appender = null;
        String appenderName = "";

        for(int i = 0; i < appenders.length; i++){
            appender = appenders[i];
            if(appender != null){
           		appenderName = appender.getName();
                atualizaAppender(appenderName, appender);
            }
        }

        return mapAppenders;
    }


    /**
     * Atualiza o mapeamento de appenders caso seja necess�rio incluir ou remover algum appender
     * @param appenderName Nome do appender a ser atualizado. Refer�ncia direta ao nome da classe do appender
     * @param appender Objeto appender a ser atualizado. Caso seja null o appender para aquele nome ser� removido
     */
    public void atualizaAppender(String appenderName, AppenderSkeleton appender){
    	
		logL.debug("Atualizando mapa de appender de log");
		
        if(appender != null){
    		logL.debug("Incluindo appender "+appenderName);
            mapAppenders.put(appenderName, appender);
        }else{
    		logL.debug("Removendo appender "+appenderName);
            mapAppenders.remove(appenderName);
        }
    }

    /**
     * Retorna o appender especificado
     * @param appenderName Nome do appender a ser retornado. Refer�ncia direta ao nome da classe do appender
     * @return AppenderSkeleton correspondente ao nome passado
     */
    public AppenderSkeleton getAppender(String appenderName){
        return (AppenderSkeleton) mapAppenders.get(appenderName);
    }

    public void close() {
    }

    public boolean requiresLayout() {
        return false;
    }
    
    /**
     * Processar e transformar os dados enviados na mensagem em uma string para envio do email
     */
    protected void processMessage(LoggingEvent evt, GcMailMessageFacade message) {

    	GcMailMessage mailMessage = (GcMailMessage) message;
    	
        StringBuffer mensagem = (message.getBody() != null ? new StringBuffer(message.getBody()) : null);

        boolean envioAviso = false;
        if (mensagem == null) {
            logL.warn("Alerta: A mensagem e NULA.");
            return;
        } else if (mensagem != null
                && mensagem.toString().equalsIgnoreCase("")) {
            logL.warn("Alerta: A mensagem e vazia.");
            return;
        } else {
            logL.debug("A mensagem eh: " + mensagem);
            envioAviso = true;
        }

        String obj = evt.getLocationInformation().getClassName();
        
        if (obj == null) {
            obj = "Indefinido";
        }
        
        GcLoggingTag logging = new GcLoggingTag(obj, mailMessage.getCategoria(), mensagem.toString());
        logging.setEnviarEmail(envioAviso);

        mailMessage.addTag(logging);
    }
    

}
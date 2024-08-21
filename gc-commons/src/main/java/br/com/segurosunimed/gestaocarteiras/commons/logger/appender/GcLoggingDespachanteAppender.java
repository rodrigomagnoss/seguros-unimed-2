package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;
//package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;
//
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//
///**
// * gestao_commons. Classe delegate para despache de serviços logger appender
// * 
// * - Despacho de log histórico
// * - Despacho de log email
// * - Despacho de log indexacao
// *   
// * @author Rodrigo Magno
// */
//public class GcLoggingDespachanteAppender {
//
//	private static final Logger logL = Logger.getLogger("logging");
//
//	private static GcLoggingDespachanteAppender instance = new GcLoggingDespachanteAppender();
//
//	public static GcLoggingDespachanteAppender getInstance() {
//
//		return instance;
//	}
//	
//	public GcLoggingDespachanteAppender() {
//		
//	}
//
//	
//	private boolean despachaLogAntes(gestao_commonsMailMessageLogging logMessage) throws Exception {
//
//		Integer levelInt = Level.toLevel(logMessage.getLevel()).toInt();
//		
//		//Enviar histórico de log
//		if (levelInt == Level.INFO_INT || levelInt == Level.WARN_INT || levelInt == Level.ERROR_INT || levelInt == Level.FATAL_INT) {
//
//			if (!gestao_commonsConstantesAzure.AMBIENTE_EXECUCAO.LOC.MODO.equals(gestao_commonsConstantesAzure.GC_AMBIENTE_EXECUCAO)) {
//				return true;
//			}
//
//		}
//
//		return false;
//	}
//	
//	private void despachaLogApos() throws Exception {
//		
//	}
//
//
//	public boolean despacharLogHistorico(gestao_commonsMailMessageLogging  msg) throws Exception {
//		
//		//logL.debug("Executar o envio de historico de log");
//		
//		gestao_commonsMailMessageLogging logMessage = (gestao_commonsMailMessageLogging) msg;
//		
//		try {
//
//			if (despachaLogAntes(logMessage)) {
//
//				String level = logMessage.getLevel();
//				String body = 
//						"[" + logMessage.getAplicacao().getSigla() + "] LOG: " + level +
//						" - ENV: "+ gestao_commonsConstantesAzure.GC_AMBIENTE_EXECUCAO + 
//						" - MENSAGEM: " + logMessage.getBody();
//				logMessage.setBody(body);
//
//				getLogSenderInstance().sendHistoricoLog(new gestao_commonsMailMessageLogging[]{logMessage});
//				
//			}
//
//			despachaLogApos();
//
//			return true;
//
//		} catch (Exception e) {
//			logL.error("Erro inesperado - despacharLogHistorico historico de log. Mensagem original: " + e.getMessage(), new gestao_commonsNoLoggingException(e));
//		}
//		return false;
//		
//	}
//
//	protected GcLogHistoricoSender getLogSenderInstance() {
//
//		return GcLogHistoricoSender.getInstance();
//	}
//	
//	
//}

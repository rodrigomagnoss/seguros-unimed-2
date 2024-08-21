package br.com.segurosunimed.gestaocarteiras.commons.logger.sender;
//package br.com.segurosunimed.gestaocarteiras.commons.logger.sender;
//
//import org.apache.log4j.Logger;
//
//	/**
//	 * gestao_commons 1.0 . Serviço de configuração de appender de log
//	 * 
//	 * @author Rodrigo Magno
//	 */
//	public class GcLogHistoricoSender {
//
//		protected Logger logL = Logger.getLogger("logging");
//
//		GcBaseAppender invConBaseAppender = new GcBaseAppender();
//		
//		private static GcLogHistoricoSender INSTANCE = new GcLogHistoricoSender();
//
//		protected GcLogHistoricoSender() {
//			super();
//		}
//
//		public static GcLogHistoricoSender getInstance() {
//			return INSTANCE;
//		}
//		
//	    /**
//	     * Envia logs para historico utilizando o endpoint do serviço informado.
//	     * 
//	     */
//	    public void sendHistoricoLog(gestao_commonsMailMessageLogging logMessages[]) throws Exception {
//	    	
//	        try {
//
//            	gestao_commonsServicosAPIAzure invConServAPI = gestao_commonsServicosAPIAzure.getInstance();
//
//	            for (int i = 0; i < logMessages.length; i++) {
//	            	
//	            	gestao_commonsMailMessageLogging logMessage = logMessages[i];
//
////	                invConServAPI.enviarHistoricoLog((gestao_commonsMailMessageLogging) logMessage);
//	            }
//	        }
//	        catch (Exception e){
//	            logL.error("Erro inesperado - Enviar mensagem de log para historico. Mensagem original: " +e, e);
//	            throw e;
//	        }
//	    }
//		
//}
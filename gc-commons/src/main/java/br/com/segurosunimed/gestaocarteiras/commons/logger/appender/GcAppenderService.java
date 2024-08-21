package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.AppenderSkeleton;
//import org.apache.log4j.Logger;

/**
 * gestao_commons 1.0 . Servico de configuração de appenders Log4j para interceptacao de envio de log
 * 
 * @author Rodrigo Magno
 */
public class GcAppenderService {

//	protected Logger logS = Logger.getLogger("service");
//
//	GcBaseAppender invConBaseAppender = new GcBaseAppender();
//	
//	private static GcAppenderService INSTANCE = new GcAppenderService();
//
//	AppenderSkeleton[] baseAppenders = null;
//	protected List listaAppender = new ArrayList();
//	
//	protected GcAppenderService() {
//		super();
//	}
//
//	public static GcAppenderService getInstance() {
//		return INSTANCE;
//	}
//
//    /**
//     * Ativar os appenders de log configurados
//     * 
//     */
//    public void ativarServicoMonitorAppender() {
//    	
//	    baseAppenders = new AppenderSkeleton[listaAppender.size()];
//	    
//	    listaAppender.toArray(baseAppenders);
//
//		registraInformacaoAppender();
//
//	    logS.info("Ativar servico monitor via appender de log");
//	    
//	    logS.info("Numero de appenders ativados: "+listaAppender.size());
//
//	}
//
//    /**
//	 * Configura appenders para interceptacao de logs
//     * @param lstAppenders Lista de appenders de log para configuracao
//     */
//	public void configurarMonitorAppenders(List lstAppenders) {
//
//    	try {
//    		listaAppender.clear();
//			listaAppender.addAll(lstAppenders);
//			
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * Registra os appenders de log configurados na instancia do servico
//	 */
//	protected void registraInformacaoAppender() {
//
//		Logger logRoot = Logger.getRootLogger();
//
//		invConBaseAppender.registraLogAppenders(baseAppenders);
//
//		logRoot.addAppender(invConBaseAppender);
//
//	}
//
//	/**
//	 * Retorna instancia de um appender de log
//	 * @param nomeAppender Nome do appender configurado
//	 * @return Instancia do appender de log referenciado pelo nome passado
//	 */
//	protected AppenderSkeleton retornaInformacaoAppender(String nomeAppender) {
//
//		return invConBaseAppender.getAppender(nomeAppender);
//
//	}

}
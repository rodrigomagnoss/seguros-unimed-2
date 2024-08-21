package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;

//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.AppenderSkeleton;
//
//import br.com.segurosunimed.gestaocarteiras.commons.GcConstantes;


/**
 * product_api 1.0 . Auxiliar para configuracao de appenders de Log
 * 
 * @author Rodrigo Magno
 */
public class GcAppenderLoggingHelper extends GcAppenderHelper {

//	private static GcAppenderLoggingHelper INSTANCE = new GcAppenderLoggingHelper();
//	
//	private GcAppenderLoggingHelper() {
//		super();
//	}
//
//	public static GcAppenderLoggingHelper getInstance() {
//		return INSTANCE;
//	}
//
//	@Override
//	public AppenderSkeleton configurarAppender() throws Exception {
//
//		GcMailAppender invConMailAppender = (GcMailAppender) super.configurarAppender();
//		
//		String emailLog = StringUtils.isNotBlank(System.getenv(GcConstantes.GC_EMAIL_LOG_ERROR)) ? 
//    			(String) System.getenv(GcConstantes.GC_EMAIL_LOG_ERROR) : 
//    				GcConstantes.MAIL.CABECALHO.DESTINATARIO_SUPORTE_EMAIL;
//		
//		invConMailAppender.set_emailErro(emailLog);
//		invConMailAppender.set_emailFatal(emailLog);
////		invConMailAppender.set_nomeAplicacao(GcConstantes.APLICACAO.NOME);
////		invConMailAppender.set_siglaAplicacao(GcConstantes.APLICACAO.SIGLA);
//		
//		return invConMailAppender;
//		
//	}
//	
//	@Override
//	@gestao_commonsTesteAcao(acao=gestao_commonsEnumAcaoTeste.ADIAR)
//	public GcMailAppender criarMailAppender() {
//		
//		GcMailAppender invConMailAppender =  super.criarMailAppender();
//		
//		invConMailAppender.setName(gestao_commonsConstantes.LOGGER.APPENDER.NOME);
//		return invConMailAppender;
//	}
//	
//	/**
//	 * Cria a instancia de um appender para envio de mensagens de email via interceptacao de envio de logs
//	 * @return Instancia do appender criado
//	 */
//	protected AppenderSkeleton initMailAppender() {
//
//		gestao_commonsLoggingMailAppender mailAppender = new gestao_commonsLoggingMailAppender();
//
//		return mailAppender;
//	}
	
}
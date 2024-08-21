package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.segurosunimed.gestaocarteiras.commons.constants.GcConstantes;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcHistMsgLog;

/**
 * gestao_commons 1.0. Auxiliar para configuração de appenders de interceptacao de envio de logs Log4j
 * 
 * @since gestao_commons 1.0
 * @author Rodrigo Magno
 */
public class GcAppenderHelper {

	private static final Logger LOGGER = Logger.getLogger("logging");
	 
	private static GcAppenderHelper INSTANCE = new GcAppenderHelper();

	protected GcAppenderHelper() {

	}

	public static GcAppenderHelper getInstance() {

		return INSTANCE;
	}

	/**
	 * Configurar appenders para envio de mensagens de E-Mail via interceptacao de envio de logs
	 * 
	 * @throws Exception
	 */
	public AppenderSkeleton configurarAppender() throws Exception {

		LOGGER.info(new GcHistMsgLog("Configurar appender para envio de email via interceptacao de log"));

		try {

			GcMailAppender mailAppender = criarMailAppender();

			return mailAppender;
			
		} catch (Exception e) {
			LOGGER.error("Erro - Configurar appender para envio de email via interceptacao de log");
			throw e;
		}

	}

	/**
	 * Configura a instancia de um appender para envio de mensagens de email via interceptacao de envio de logs
	 * @return Instancia do appender configurado
	 */
	public GcMailAppender criarMailAppender() {

		LOGGER.info(new GcHistMsgLog("Configurar appender para interceptacao de log"));
		
		GcMailAppender mailAppender = (GcMailAppender) initMailAppender();
		
		try {
			mailAppender.activateOptions();
			mailAppender.setThreshold(Level.INFO);
			mailAppender.setName(GcConstantes.MAIL.APPENDER.NOME);
			
		} catch (Exception e) {
			LOGGER.error("Erro inesperado - Criar appender para interceptar envio mensagem log. Mensagem original: " + e, e);
			mailAppender = null;
		}

		return mailAppender;
	}

	/**
	 * Cria a instancia de um appender para envio de mensagens de email via interceptacao de envio de logs
	 * @return Instancia do appender criado
	 */
	protected AppenderSkeleton initMailAppender() {

		LOGGER.info(new GcHistMsgLog("Criar o appender para interceptacao de log"));

		GcMailAppender mailAppender = new GcMailAppender();

		return mailAppender;
	}

}

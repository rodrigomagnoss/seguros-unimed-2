package br.com.segurosunimed.gestaocarteiras.commons.mail.sender;

import java.net.URI;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import br.com.segurosunimed.gestaocarteiras.commons.exception.GcException;
import br.com.segurosunimed.gestaocarteiras.commons.exception.GcNoLoggingException;
import br.com.segurosunimed.gestaocarteiras.commons.exception.GcNoMailLoggingException;
import br.com.segurosunimed.gestaocarteiras.commons.helper.GcHelper;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcMailMessage;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcMailMessageFacade;

/**
 * Classe Singleton responsável pelo envio dos emails, utilizando serviço do GC
 * API.
 */
@Service
public class GcSpringMailMessageSender {

	@Value("${spring.mail.host}")
	private String mailSmtplHost;

	@Value("${spring.mail.port}")
	private String mailSmtpPort;

	@Value("${spring.mail.username}")
	private String mailUsername;

	@Value("${spring.mail.password}")
	private String mailPassword;

	Logger logL = Logger.getLogger(GcSpringMailMessageSender.class);

	// private final RecipientType SEND_TO = Message.RecipientType.TO;
	// private final RecipientType SEND_CC = Message.RecipientType.CC;
	// private final RecipientType SEND_BCC = Message.RecipientType.BCC;

	public void sendMail(GcMailMessageFacade mail) throws Exception {
		sendMail(new GcMailMessageFacade[] { mail });
	}

	/**
	 * Envia mails utilizando o endpoint do serviço informado.
	 * 
	 */
	public void sendMail(GcMailMessageFacade mailMessages[]) throws Exception {

		try {

			if (StringUtils.isBlank(this.mailSmtplHost)) {
				logL.warn("Servidor SMTP nao encontrado. Email nao enviado.");
				return;
			}

			for (int i = 0; i < mailMessages.length; i++) {

				GcMailMessageFacade message = mailMessages[i];
				//
				// //MimeMessage msg = new MimeMessage(ss);
				// MimeMessage msg = getObjetoMensagem(prop);
				// populaEnderecamento(message, msg);
				// definePrioridade(msg, message.getPrioridade());
				//
				// MimeBodyPart part = new MimeBodyPart();
				//
				// String formato = getFormato(message);
				// String corpo = message.getBody();
				//
				// part.setContent( (corpo != null? corpo : ""), formato );
				//
				// Multipart mp = new MimeMultipart();
				// mp.addBodyPart(part);
				//
				// msg.setContent(mp);
				//
				// msg.setSentDate( new Date() );
				//
				// enviarEmail((GcMailMessage) message);

				sendPlainTextEmail(message.getToAddress(), message.getSubject(), message.getBody());
			}
		} catch (MessagingException e) {
			// ATENÇÃO: este log deve sempre estar em nivel WARN para evitar loop do Log
			// Monitor
			// caso ocorra algum erro no envio de mensagens
			// by Rodrigo Magno - 29/03/2005
			logL.warn("Alerta - Nao foi possivel enviar email. Mensagem original: " + e, e);
			throw new Exception(e);
		} catch (Exception e) {
			throw e;
		}
	}

	public void sendPlainTextEmail(String toAddress, String subject, String message)
			throws MessagingException, javax.mail.MessagingException {

		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", mailSmtplHost);
		properties.put("mail.smtp.port", mailSmtpPort);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.user", mailUsername);

		// creates a new session, no Authenticator (will connect() later)
		Session session = Session.getDefaultInstance(properties);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(mailUsername));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		// set plain text message
		msg.setText(message);

		// sends the e-mail
		Transport t = session.getTransport("smtp");
		t.connect(mailUsername, mailPassword);
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();

	}

	protected String enviarEmail(GcMailMessage message) throws Exception {

		try {

			logL.info("Executar servico enviar mensagem email");

			GcHelper gcHelper = GcHelper.getInstance();
			// GcTokenCacheAspect invConTokenCache = GcTokenCacheAspect.getInstance();

			HttpClient httpclient = gcHelper.getHttpClientForSSL();

			URI uri = new URI("");
			//
			HttpPost httppost = new HttpPost(uri);
			// String jwtToken = jwtTokenAccountService.createToken(fullClaims,
			// accountDTO.getUsername());
			// httppost.setHeader("Authorization", jwtToken);
			httppost.setHeader("Content-Type", "application/json");
			//
			String body = message.toMailMessageJson();
			StringEntity reqEntity = new StringEntity(body);
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			String strEntity = EntityUtils.toString(response.getEntity());

			if (response.getStatusLine() != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_ACCEPTED) {
					logL.debug("Resposta de retorno da requisicao do servico de envio de email: " + strEntity);
					String respMsgErro = response.getStatusLine().getReasonPhrase();
					String msgError = "Erro - Enviar email. HTTP error code: " + statusCode + " -  Mensagem original: "
							+ respMsgErro;
					logL.error(msgError, new GcNoMailLoggingException());
					message.setMensagemEnviada(false);
					throw new GcException().getBadRequestFileException(msgError);
				}
			}

			return strEntity;

		} catch (Exception e) {
			logL.error("Erro inesperado - Executar servico para enviar email. Mensagem original: " + e,
					new GcNoLoggingException(e));
			throw new GcNoMailLoggingException(e);
		}

	}

	/**
	 * Recupera o formato da mensagem a ser enviada
	 * 
	 * @author Rodrigo Magno
	 * 
	 */
	protected String getFormato(GcMailMessageFacade mail) {

		String formato = mail.getMimeType();

		if (formato.equalsIgnoreCase("T")) {
			formato = "text/plain;";
		} else if (formato.equalsIgnoreCase("H")) {
			formato = "text/html;";
		}

		return formato;
	}

	/**
	 * Popula os campos de enderecamento e assunto.
	 * 
	 * @throws javax.mail.MessagingException
	 */
	protected void populaEnderecamento(GcMailMessageFacade mail, MimeMessage msg)
			throws MessagingException, javax.mail.MessagingException {

		if (!StringUtils.isBlank(mail.getReplyTo())) {
			msg.setReplyTo(parseAddress(mail.getReplyTo()));
		}

		if (!StringUtils.isBlank(mail.getSender())) {
			msg.setHeader("Sender", mail.getSender());
		}

		if (!StringUtils.isBlank(mail.getFromAddress())) {
			msg.addFrom(parseAddress(mail.getFromAddress()));
		} else {
			msg.setFrom();
		}

		// if (!StringUtils.isBlank(mail.getToAddress())) {
		// msg.setRecipients(SEND_TO, parseAddress(mail.getToAddress()));
		// }
		//
		// if (!StringUtils.isBlank(mail.getToCcAddress())) {
		// msg.setRecipients(SEND_CC, parseAddress(mail.getToCcAddress()));
		// }
		//
		// if (!StringUtils.isBlank(mail.getToBccAddress())) {
		// msg.setRecipients(SEND_BCC, parseAddress(mail.getToBccAddress()));
		// }

		msg.setSubject(mail.getSubject());
	}

	/**
	 * Define a prioridade da mensagem.
	 * 
	 * @throws javax.mail.MessagingException
	 * 
	 */
	protected void definePrioridade(MimeMessage msg, int prioridade)
			throws MessagingException, javax.mail.MessagingException {
		String pr = null;
		switch (prioridade) {
			case GcMailMessageFacade.PRIORIDADE_ALTA:
				pr = "HIGH";
				break;

			case GcMailMessageFacade.PRIORIDADE_NORMAL:
				pr = "NORMAL";
				break;

			case GcMailMessageFacade.PRIORIDADE_BAIXA:
				pr = "LOW";
				break;

			default:
				pr = "NORMAL";
				break;
		}
		msg.setHeader("X-PRIORITY", pr);
	}

	/**
	 * Retorna array de InternetAddress a partir da String informada, com emails
	 * separados por vírgula.
	 * 
	 */
	protected InternetAddress[] parseAddress(String addressStr) {
		try {
			addressStr = StringUtils.replace(addressStr, ";", ",");
			return InternetAddress.parse(addressStr, true);
		} catch (AddressException e) {
			logL.error("Nao foi possivel fazer parse no endereco [" + addressStr + "].", e);
			return null;
		}
	}

	/**
	 * Cria um objeto mensagem
	 * 
	 * @author Rodrigo Magno
	 * 
	 */
	protected MimeMessage getObjetoMensagem(Properties prop) throws Exception {
		Session sess = Session.getInstance(prop, null);
		MimeMessage msg = new MimeMessage(sess);
		return msg;
	}

}
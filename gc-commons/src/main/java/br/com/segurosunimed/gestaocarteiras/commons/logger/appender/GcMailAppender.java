package br.com.segurosunimed.gestaocarteiras.commons.logger.appender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import br.com.segurosunimed.gestaocarteiras.commons.constants.GcConstantes;
import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcAplicacaoTag;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcMailMessage;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcMailMessageFacade;
import br.com.segurosunimed.gestaocarteiras.commons.mail.message.GcMailMessageLogging;
import br.com.segurosunimed.gestaocarteiras.commons.mail.sender.GcMailMessageSender;
import br.com.segurosunimed.gestaocarteiras.commons.mail.util.GcMailMessageStringUtil;

/**
 * Gc 1.0. Appender Log4J para envio de emails.
 * 
 * @author Rodrigo Magno
 */
public class GcMailAppender extends GcBaseAppender {

	private static final Logger logL = Logger.getLogger("logging");

	//Campos de atributo de classe
	private Locale locale = new Locale("pt", "BR");

	private SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm:ss", locale);

	private String _emailEmpresa = "";

	private String _emailErro = "";

	private String _emailFatal = "";

	private String _nomeAplicacao = "";

	private String _siglaAplicacao = "";

	private String _nomeServidor = "";

	private String _IPServidor = "";

	private String _htmlBaseURI = "";

	private String _htmlBase = "";

	private String _mailSmtpHost = "";

	private boolean enviadoMail = true;

	private boolean enviadoLog = true;
	
	private boolean modoTeste = false;

	public GcMailAppender() {

	}

	/**
	 * Carrega o HTML base contraucao do email de alerta.
	 */
	public void activateOptions() {

		super.activateOptions();

		try {
			URI uri = Objects.requireNonNull(GcMailAppender.class.getClassLoader().getResource("htmlbase.html")).toURI();
			loadHtmlBase(uri);

		} catch (URISyntaxException e) {
			
			logL.error("Erro ao carregar arquivo html para log d e-mail", e);
		}

	}

	/**
	 * Guarda as informacoes da aplicacao sigla, nome, emails, etc.
	 * 
	 */
	protected GcAplicacaoTag getAplicacao() {

		GcAplicacaoTag app = new GcAplicacaoTag();
		app.setSigla(_siglaAplicacao);
		app.setNome(_nomeAplicacao);
		app.setEmailRemetente(_emailEmpresa);
		app.setEmailError(_emailErro);
		app.setEmailFatal(_emailFatal);
		app.setIp(_IPServidor);
		app.setHost(_nomeServidor);

		return app;
	}

	public void append(LoggingEvent evt) {

		super.append(evt);
		
	}


	/**
	 * Despacha email.
	 * 
	 */
	protected boolean despacharEmail(GcMailMessage mailMessage) {

		try {

			if (despachaEmailAntes(mailMessage)) {
				getMailSenderInstance().sendMail(_mailSmtpHost, mailMessage);
			}

			despachaEmailApos();

			setEnviadoMail(mailMessage.isMensagemEnviada());
			
			return true;
		} catch (Exception e) {
			logL.warn("Erro inesperado - Despachar email. Mensagem original: " + e, e);
			setEnviadoMail(mailMessage.isMensagemEnviada());
			return false;
		}
		
	}

	protected GcMailMessageFacade getMailMessage() {
		
		return new GcMailMessage();
	}

	protected GcMailMessageSender getMailSenderInstance() {

		return GcMailMessageSender.getInstance();
	}

	protected boolean despachaEmailAntes(GcMailMessageFacade mail) throws Exception {

		return true;
	}

	protected void despachaEmailApos() throws Exception {

	}


	/**
	 * Substituir os parametros do objeto da mensagem de email no template html (htmlBase), gerando uma nova string para ser utilizada no corpo do email.
	 * 
	 * @param message Objeto que representa a mensagem de email
	 * @param categoria Categoria da mensagem de email
	 * @param objeto Nome do objeto de origem da mensagem de email
	 * @param mensagem Conteúdo da mensagem email
	 * @param usuario Nome do usuário que atual
	 * @param complemento Complemento de dados da mensagem de email
	 * 
	 * @return Conteúdo do template da mensagem de email com os dados dinâmicos substituidos por seus respectivos parâmetros
	 */
	public String aplicaHtmlBase(GcMailMessage message, String categoria, String objeto, String mensagem, String usuario, String complemento) {

		GcAplicacaoTag app = message.getAplicacao();
		String nomeApp = app.getSigla() + " - " + app.getNome();

		return aplicaHtmlBase(categoria, nomeApp, app.getIp(), objeto, message.getData(), mensagem, usuario, complemento);
	}

	/**
	 * Substituir os parametros da mensagem de email no template html (htmlBase), gerando uma nova string para ser utilizada no corpo do email.
	 * 
	 * @param categoria Categoria do alerta
	 * @param aplicacao Nome da aplicacao
	 * @param servidor Nome do servidor
	 * @param objeto Nome do objeto de origem da mensagem de email
	 * @param mensagem Conteúdo da mensagem email
	 * @param data Data da mensagem
	 * @param usuario Nome do usuário que atual
	 * @param complemento Complemento de dados da mensagem de email
	 * @return Conteúdo do template da mensagem de email com os dados dinâmicos substituidos por seus respectivos parâmetros
	 */
	public String aplicaHtmlBase(String categoria, String aplicacao, String servidor, String objeto, Date data, String mensagem, String usuario, String complemento) {

		String newHtml = StringUtils.replace(_htmlBase, "${CATEGORIA_ALERTA}", categoria);
		newHtml = StringUtils.replace(newHtml, "${APLICACAO}", aplicacao);
		newHtml = StringUtils.replace(newHtml, "${SERVIDOR}", servidor);
		newHtml = StringUtils.replace(newHtml, "${OBJETO}", objeto);
		newHtml = StringUtils.replace(newHtml, "${DATA}", dateFormatter.format(data));
		newHtml = StringUtils.replace(newHtml, "${MENSAGEM}", mensagem);
		newHtml = StringUtils.replace(newHtml, "${COMPLEMENTO}", mensagem);

		// oculta informa��o de usu�rio
		if (StringUtils.isBlank(usuario)) {
			newHtml = StringUtils.replace(newHtml, "<!-- usuario -->", "<!-- usuario ");
			newHtml = StringUtils.replace(newHtml, "<!-- /usuario -->", " /usuario -->");
		} else {
			newHtml = StringUtils.replace(newHtml, "${USUARIO}", usuario);
		}

		if (complemento == null) {
			complemento = " ";
		}
		newHtml = StringUtils.replace(newHtml, "${COMPLEMENTO}", complemento);

		return (StringUtils.isNotBlank(newHtml) ? newHtml : mensagem);
	}


	/**
	 * Monta e envia a mensagem de email
	 * 
	 */
	protected void enviaMail(GcMailMessageFacade message) {

		try {
			
			enviaMailAntes(message);
			
			GcMailMessage mailMessage = (GcMailMessage) getMailMessage();

			mailMessage.setFromAddress(message.getFromAddress());
			mailMessage.setToAddress(message.getToAddress());
			mailMessage.setSubject(message.getSubject());

			String mimetype = message.getMimeType();

			mailMessage.setMimeType((mimetype != null ? mimetype : "text/html"));

			String msg = message.getBody();

			mailMessage.setBody(msg);

			int prioridade = GcMailMessage.PRIORIDADE_NORMAL;

			mailMessage.setPrioridade(prioridade);

//			if (message.getAnexos() != null) {
//				mailMessage.montaAnexos(message, mailMessage);
//			}

			if (enviaMailAntes(mailMessage)) {
				getMailSenderInstance().sendMail(_mailSmtpHost, mailMessage);
			}

			enviaMailApos(mailMessage);

		} catch (Exception e) {
			logL.warn("Erro inesperado - Nao foi possivel enviar mail: " + message.getSubject(), e);
		}
	}

	/**
	 * Metodo template chamado antes da execução do método padrão utilizado para verificar se o email pode ser enviado
	 * @param message Objeto de instância da mensagem a ser enviada 
	 * @return True se o email pode ser enviado, false caso contrário
	 * @throws Exception
	 */
	protected boolean enviaMailAntes(GcMailMessageFacade message) throws Exception {

		return true;
	}

	/**
	 * Metodo template chamado após a execução do método padrão para lógicas complementares
	 * @param message Objeto de instância da mensagem enviada 
	 * @throws Exception
	 */
	protected void enviaMailApos(GcMailMessageFacade message) throws Exception {

	}

	/**
	 * Recuperar os dados do array de stack trace da mensagem de log montando uma string única para o envio de e-mail
	 * @param message Objeto de instância da mensagem a ser enviada 
	 * @return String única que representa os dados do array de stack trace
	 */
	public String montarStackTraceLog(GcMailMessageLogging logMessage) {

		if(logMessage != null &&  logMessage.getExcecaoTag() != null && logMessage.getExcecaoTag().getStackTrace() != null){

			String[] stacks = logMessage.getExcecaoTag().getStackTrace();
			StringBuffer sbStacks = new StringBuffer();

			if (stacks != null && stacks.length > 0) {
				sbStacks
//				.append("<p><b>Stack trace:</b><br><code>")
				.append(GcMailMessageStringUtil.getInstance().StringArrayToString(stacks, "\\n"));
//				.append("</code></p>");
			}

			return sbStacks.substring(0, 5).toString();
		}

		return "Sem pilha de rastreamento do erro";
		
	}

	/**
	 * Carrega em memoria o html contido no arquivo html informado.
	 * <p>
	 * Este arquivo deve conter uma estrutura de template html para servir de modelo na composicao dos emails de alerta.
	 * </p>
	 */
	protected void loadHtmlBase(URI uri) {

		StringBuffer html = new StringBuffer();
		String htmlBase = null;

		// se informou um arquivo
		try {
			if (uri != null) // se informado um arquivo.
			{
				File file = new File(uri);
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);

				String lineRead = br.readLine();
				while (lineRead != null) {
					html.append(lineRead);
					lineRead = br.readLine();
				}
				htmlBase = html.toString();
			}
		} catch (Exception e) {
			logL.warn("Erro inesperado - Carregar o html informado para envio de emails de alerta. Estar� sendo usado o layout default. ");
		}

		InputStream isArquivoBase = null;
		try {
			if (htmlBase == null) {
				isArquivoBase = this.getClass().getResourceAsStream("htmlbase.html");
				htmlBase = lerArquivo(isArquivoBase);
			}
		} catch (Exception e) {
			logL.warn("Erro inesperado - Nao foi possivel encontrar arquivo modelo HTML para mensagem de emai.");
		} finally {
			if (isArquivoBase != null) {
				try {
					isArquivoBase.close();
				} catch (IOException e1) {
					logL.error("Erro - Fechar o fluxo para o arquivo de modelo base. Mensagem original: " + e1, e1);
				}
			}
		}

		if (htmlBase == null) {
			StringBuffer sb = new StringBuffer();

			sb
			.append("<h2>Alerta Monitor Gc</h2>").append("<strong>${NIVEL_ALERTA}</strong><br>")
			.append("Aplicação: <strong>${APLICACAO}</strong><br>")
			.append("Servidor: ${SERVIDOR}<br>")
			.append("Objeto: <strong>${OBJETO}</strong><br>")
			.append("Horário: ${DATA}<br>")
//			.append("<!-- usuario -->Usuário: ${USUARIO}<br><!-- /usuario -->")
			.append("Mensagem:<p> ${MENSAGEM}</p>")
			.append("<p>${COMPLEMENTO}</p>");

			htmlBase = sb.toString();
		}

		set_htmlBase(htmlBase);
	}

	/**
	 * Ler os dados de um arquivo base de template de mensagem
	 * @param isArquivoBase Dados binários de um arquivo template de mensagens
	 * @return String de dados do arquivo de template
	 */
	protected String lerArquivo(InputStream isArquivoBase) {

		try {
			StringBuffer buffer = new StringBuffer();
			if (isArquivoBase != null) {
				byte[] bytesArq = new byte[1024];

				while (isArquivoBase.read(bytesArq) != -1) {
					buffer.append(new String(bytesArq));
					bytesArq = new byte[1024]; // para limpar o array
				}
				return buffer.toString();
			}
		} catch (Exception e) {
			logL.warn("Erro inesperado - Nao foi possivel encontrar o arquivo de modelo HTML para mensagem de email.");
		}

		return null;
	}

	/**
	 * Ler um array de String devolvendo o seu conteúdo em formato String, quebrando cada elemento, com o grupo de caracteres informado, ao final de cada linha.
	 * @param st Array de strings de dados
	 * @param lineBreak Define o separador de cada item do array de strings
	 * @return Conteudo do array no formato de string única
	 */
	public String StringArrayToString(String[] st, String lineBreak) {

		StringBuffer bf = new StringBuffer();

		if (lineBreak == null)
			lineBreak = "\n";

		if (st != null && st.length > 0) {
			for (int n = 0; n < st.length; n++) {
				bf.append(st[n]);
				bf.append(lineBreak);
			}
		}

		return bf.toString();
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public SimpleDateFormat getDateFormatter() {
		return dateFormatter;
	}

	public void setDateFormatter(SimpleDateFormat dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

	public String get_emailEmpresa() {
		return _emailEmpresa;
	}

	public void set_emailEmpresa(String _emailEmpresa) {
		this._emailEmpresa = _emailEmpresa;
	}

	public String get_emailErro() {
		return _emailErro;
	}

	public void set_emailErro(String _emailErro) {
		this._emailErro = _emailErro;
	}

	public String get_emailFatal() {
		return _emailFatal;
	}

	public void set_emailFatal(String _emailFatal) {
		this._emailFatal = _emailFatal;
	}

	public String get_nomeAplicacao() {
		return _nomeAplicacao;
	}

	public void set_nomeAplicacao(String _nomeAplicacao) {
		this._nomeAplicacao = _nomeAplicacao;
	}

	public String get_siglaAplicacao() {
		return _siglaAplicacao;
	}

	public void set_siglaAplicacao(String _siglaAplicacao) {
		this._siglaAplicacao = _siglaAplicacao;
	}

	public String get_nomeServidor() {
		return _nomeServidor;
	}

	public void set_nomeServidor(String _nomeServidor) {
		this._nomeServidor = _nomeServidor;
	}

	public String get_IPServidor() {
		return _IPServidor;
	}

	public void set_IPServidor(String _IPServidor) {
		this._IPServidor = _IPServidor;
	}

	public String get_htmlBaseURI() {
		return _htmlBaseURI;
	}

	public void set_htmlBaseURI(String _htmlBaseURI) {
		this._htmlBaseURI = _htmlBaseURI;
	}

	public String get_htmlBase() {
		return _htmlBase;
	}

	public void set_htmlBase(String _htmlBase) {
		this._htmlBase = _htmlBase;
	}

	public String get_mailSmtpHost() {
		return _mailSmtpHost;
	}

	public void set_mailSmtpHost(String _mailSmtpHost) {
		this._mailSmtpHost = _mailSmtpHost;
	}

//	public void setModoExecucao(String modoExecucao) {
//		this.modoExecucao = modoExecucao;
//	}
//
//	public String getModoExecucao() {
//		return this.modoExecucao;
//	}

	public boolean isAmbienteProducao(){
		return GcConstantes.AMBIENTE_EXECUCAO.PRD.MODO.equals(System.getenv("GC_AMBIENTE_EXECUCAO"));
	}

	public boolean isAmbienteLocal(){
		return GcConstantes.AMBIENTE_EXECUCAO.LOC.MODO.equals(System.getenv("GC_AMBIENTE_EXECUCAO"));
	}

	public boolean isAmbienteDesenvolvimento(){
		return GcConstantes.AMBIENTE_EXECUCAO.DEV.MODO.equals(System.getenv("GC_AMBIENTE_EXECUCAO"));
	}

	public boolean isAmbienteHomologacao(){
		return GcConstantes.AMBIENTE_EXECUCAO.HOM.MODO.equals(System.getenv("GC_AMBIENTE_EXECUCAO"));
	}

	public boolean isAmbienteTeste(){
		return GcConstantes.AMBIENTE_EXECUCAO.TST.MODO.equals(System.getenv("GC_AMBIENTE_EXECUCAO"));
	}

	public boolean isModoTeste(){
		return modoTeste;
	}

	public String getModoTeste(){
		return isModoTeste() ? GcConstantes.SIM : GcConstantes.NAO;
	}
	
	public void setModoTeste(boolean modoTeste){
		this.modoTeste = modoTeste;
	}
	
	public boolean isEnviadoMail() {
		return enviadoMail;
	}

	public void setEnviadoMail(boolean enviadoMail) {
		this.enviadoMail = enviadoMail;
	}

	public boolean isEnviadoLog() {
		return enviadoLog;
	}

	public void setEnviadoLog(boolean enviadoLog) {
		this.enviadoLog = enviadoLog;
	}

}
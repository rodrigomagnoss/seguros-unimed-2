package br.com.segurosunimed.gestaocarteiras.commons.mail.message;

import java.io.Serializable;
import java.util.Date;

import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEnumTipoLog;
import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcExcecaoTag;


/**
 * Classe ancestral para envio via email log para monitor.
 */
public class GcMailMessageLogging extends GcMailMessage implements Serializable
{
	private static final long serialVersionUID = -7899465199245302642L;

	public static final String LEVEL_INFO = "INFO";
	public static final String LEVEL_DEBUG = "DEBUG";
	public static final String LEVEL_ERROR = "ERROR";
	public static final String LEVEL_FATAL = "FATAL";
	public static final String LEVEL_WARN = "WARN";

	private String siglaAplicacao;
	private String nomeAplicacao;
	private Date   dataHoraEnvio;
	private String usuarioCorrente;
	private String nomeServidor;
	private String level;

	private String metodo;
	private String classe;
	private String linha;
	private String categoria;
	private GcExcecaoTag excecaoTag;

	private boolean logEnviado = true;

	public GcExcecaoTag getExcecaoTag() {
		return excecaoTag;
	}

	public void setExcecaoTag(GcExcecaoTag excecaoTag) {
		this.excecaoTag = excecaoTag;
	}

	/**
	 * Identifica o tipo de objeto: CLASSE, LINK, BUSCA, etc.
	 */
	private String tipoObjeto;

	private String emailFatal;
	private String emailErro;
	private String emailEmpresa;



	public Date getDataHoraEnvio()
	{
		return dataHoraEnvio;
	}

	public String getEmailEmpresa()
	{
		return emailEmpresa;
	}

	public String getEmailErro()
	{
		return emailErro;
	}

	public String getEmailFatal()
	{
		return emailFatal;
	}

	public String getNomeAplicacao()
	{
		return nomeAplicacao;
	}

	public String getSiglaAplicacao()
	{
		return siglaAplicacao;
	}

	public String getUsuarioCorrente()
	{
		return usuarioCorrente;
	}

	public void setDataHoraEnvio(Date date)
	{
		dataHoraEnvio = date;
	}

	public void setEmailEmpresa(String string)
	{
		emailEmpresa = string;
	}

	public void setEmailErro(String string)
	{
		emailErro = string;
	}

	public void setEmailFatal(String string)
	{
		emailFatal = string;
	}

	public void setNomeAplicacao(String string)
	{
		nomeAplicacao = string;
	}

	public void setSiglaAplicacao(String string)
	{
		siglaAplicacao = string;
	}

	public void setUsuarioCorrente(String string)
	{
		usuarioCorrente = string;
	}

	public String getCategoria()
	{
		return categoria;
	}

	public void setCategoria(String string)
	{
		categoria = string;
	}

	
	public String getTipoObjeto()
	{
		return tipoObjeto;
	}

	public void setTipoObjeto(String string)
	{
		tipoObjeto = string;
	}

	public String getNomeServidor()
	{
		return nomeServidor;
	}

	public void setNomeServidor(String string)
	{
		nomeServidor = string;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String toLogMessageJson(){
		
		String level = this.level != null ? this.level : GcEnumTipoLog.INFO.toString();
		String body = 	"{" +
			    "\"type\": \""+GcEnumTipoLog.valueOf(level).tipo()+"\","
			   + "\"message\": \""+this.getBody().replace("\"", "\\\"")+"\""+
		"}";

		return body;

	}

	public boolean isLogEnviado() {
		return logEnviado;
	}

	public void setLogEnviado(boolean logEnviado) {
		this.logEnviado = logEnviado;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	
	public String getLogID(){
		return this.classe +"-"+this.metodo+"-"+this.linha;	
	}
	
	public String toString()
	{

		String level = this.getLevel();
		String categoria = this.getCategoria();
		String metodo = this.getMetodo();
		String linha = this.getLinha();
		String modo = this.getModoExecucao();
		String mensagem = this.getMensagem();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("LOG: "+level);
		sb.append("- MODO - "+modo);
		sb.append("- CATEGORIA - "+categoria);
		sb.append("- METODO - "+metodo+ " ["+linha+"]");
		sb.append("- MENSAGEM - "+mensagem);
		
		return sb.toString() ;
	}
	
}
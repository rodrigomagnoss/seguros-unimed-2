package br.com.segurosunimed.gestaocarteiras.commons.logger.tag;

import java.util.Date;

/**
 * Classe para rotulos utilizados nas mensagens de log referentes a perfis
 *  
 * @author Rodrigo Magno
 *
 */
public class GcProfileTag {

   private static final long serialVersionUID = 1L;
	
   	public static String MARCADOR = "+";
	private String nome;
    private String ip;
    private String url;
    private Date datahora;
    private String logId;
    private String logProfile;
    
    private String marcador 	= "";
    
    public GcProfileTag(){

    	setNome("");
    	setIp("");
    	setUrl("");
    	setDatahora(null);
    	setLogId(null);
    	setLogProfile(null);
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the datahora
	 */
	public Date getDatahora() {
		return datahora;
	}

	/**
	 * @param datahora the datahora to set
	 */
	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogProfile() {
		return logProfile;
	}

	public void setLogProfile(String logProfile) {
		this.logProfile = logProfile;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getMarcador() {
		return marcador;
	}

	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}


}
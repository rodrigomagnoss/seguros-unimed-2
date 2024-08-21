package br.com.segurosunimed.gestaocarteiras.commons.logger.tag;

import br.com.segurosunimed.gestaocarteiras.commons.constants.GcConstantes;

/**
 * Classe para rotulos utilizados nas mensagens de log referentes a aplicacao
 *  
 * @author Rodrigo Magno
 *
 */
public class GcAplicacaoTag implements GcTagFacade {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "aplicacao";
    private static final String[] tagProps = { "sigla", "nome", "ip", "host",
            "lifecycle", "emailRemetente", "emailError", "emailFatal" };
    
    private String sigla = GcConstantes.APLICACAO.SIGLA;
    private String nome = GcConstantes.APLICACAO.NOME;
    private String ip;
    private String host;
    private String lifecycle;
    private String emailRemetente;
    private String emailError;
    private String emailFatal;
    
    public GcAplicacaoTag() {
    }
    
    public GcAplicacaoTag(String sigla, String nome, String ip, String lifecycle,
            String emailRemetente, String emailError, String emailFatal) {
        this.sigla = sigla;
        this.nome = nome;
        this.ip = ip;
        this.lifecycle = lifecycle;
        this.emailRemetente = emailRemetente;
        this.emailError = emailError;
        this.emailFatal = emailFatal;
    }
    
    public String getTAG() {
        return TAG;
    }

    public String getEmailError() {
        return emailError;
    }
    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }
    public String getEmailFatal() {
        return emailFatal;
    }
    public void setEmailFatal(String emailFatal) {
        this.emailFatal = emailFatal;
    }
    public String getEmailRemetente() {
        return emailRemetente;
    }
    public void setEmailRemetente(String emailRemetente) {
        this.emailRemetente = emailRemetente;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSigla() {
        return sigla;
    }
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    public String getLifecycle() {
        return lifecycle;
    }
    public void setLifecycle(String lifecycle) {
        this.lifecycle = lifecycle;
    }

	@Override
	public String toTagString() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

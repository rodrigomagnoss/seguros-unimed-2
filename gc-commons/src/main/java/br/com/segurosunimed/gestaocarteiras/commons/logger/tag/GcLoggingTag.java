package br.com.segurosunimed.gestaocarteiras.commons.logger.tag;

/**
 * Classe para rotulos utilizados nas mensagens de log referentes a logs da aplicacao
 *  
 * @author Rodrigo Magno
 *
 */

public class GcLoggingTag implements GcTagFacade {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "MSG_LOG";
    private static final String[] tagProps = { "objeto", "tipo", "enviarEmail","mensagem" };

    private String objeto;
    private String tipo;
    private String mensagem;
    private boolean enviarEmail = true;
    
    public GcLoggingTag() {
    }
    
    public GcLoggingTag(String objeto, String tipo, String mensagem) {
        this.objeto = objeto;
        this.tipo = tipo;
        this.mensagem = mensagem;
    }
    
    public String getTAG() {
        return TAG;
    }

    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public String getObjeto() {
        return objeto;
    }
    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public boolean isEnviarEmail() {
        return enviarEmail;
    }
    public void setEnviarEmail(boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

	@Override
	public String toTagString() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

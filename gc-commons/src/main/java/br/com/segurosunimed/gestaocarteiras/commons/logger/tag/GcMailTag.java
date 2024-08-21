package br.com.segurosunimed.gestaocarteiras.commons.logger.tag;

/**
 * Classe para rotulos utilizados nas mensagens de log referentes a email
 *  
 * @author Rodrigo Magno
 *
 */
public class GcMailTag implements GcTagFacade {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "email";
    private boolean logging = false;
    private boolean assincrono = false;
    private static final String[] tagProps = { "from", "replyTo", "sender",
            "to", "toCc", "toBcc", "subject", "priority", "contentType",
            "mensagem" };
    
    private String from;
    private String fromName;
    private String sender;
    private String replyTo;
    private String to;
    private String toName;
    private String toCc;
    private String toBcc;
    private String subject;
    private String mensagem;
    private String priority;
    private String contentType = "text/html";
    
    public String getTAG() {
        return TAG;
    }

   
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToBcc() {
        return toBcc;
    }

    public void setToBcc(String toBcc) {
        this.toBcc = toBcc;
    }

    public String getToCc() {
        return toCc;
    }

    public void setToCc(String toCc) {
        this.toCc = toCc;
    }

	public boolean isLogging() {
		return this.logging;
	}

	public void setLogging(boolean logging) {
		this.logging = logging;
	}

	public boolean isAssincrono() {
		return assincrono;
	}

	public void setAssincrono(boolean assincrono) {
		this.assincrono = assincrono;
	}


	@Override
	public String toTagString() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public String getFromName() {
		return fromName;
	}


	public void setFromName(String fromName) {
		this.fromName = fromName;
	}


	public String getToName() {
		return toName;
	}


	public void setToName(String toName) {
		this.toName = toName;
	}
    
}

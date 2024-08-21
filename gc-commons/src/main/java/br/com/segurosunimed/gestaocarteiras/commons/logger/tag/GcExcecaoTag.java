package br.com.segurosunimed.gestaocarteiras.commons.logger.tag;

import org.apache.commons.lang3.ArrayUtils;

import br.com.segurosunimed.gestaocarteiras.commons.mail.util.GcMailMessageStringUtil;

/**
 * Classe para rotulos utilizados nas mensagens de log referentes a excecoes
 *  
 * @author Rodrigo Magno
 *
 */
public class GcExcecaoTag implements GcTagFacade {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "excecao";
    private static final String[] tagProps = { "objeto", "tipo", "enviarEmail","mensagem" };
    
    private String classe;
    private String metodo;
    private String linha;
    private String excecao;
    private String mensagem;
    
    public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public String getExcecao() {
		return excecao;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	private boolean enviarEmail = true;
    
	/**
	 * Guarda o resultado de Throwable.stackTrace() quando houver Exception;
	 */
	private String[] stackTrace;
    private GcStackTraceTag stackTraceTag;

    
    public GcExcecaoTag() {
    }
    
    public GcExcecaoTag(Throwable _throw) {

        this.mensagem 	= _throw.getMessage();
        this.classe 	= _throw.getClass().getSimpleName();
        
        
        if (_throw.getStackTrace() != null) {
        	String[] stackTrace = ArrayUtils.toStringArray(_throw.getStackTrace() );
        	setStackTrace(stackTrace);
        }

        enviarEmail = true;

    }
    
    public void setStackTrace(String[] trace) {
    	
    	stackTrace = trace;
    	
        stackTraceTag = new GcStackTraceTag(GcMailMessageStringUtil.getInstance()
                .StringArrayToString(trace));
    }

	public GcStackTraceTag getStackTraceTag() {
		return stackTraceTag;
	}

	public void setStackTraceTag(GcStackTraceTag stackTraceTag) {
		this.stackTraceTag = stackTraceTag;
	}

	public String[] getStackTrace() {
		return stackTrace;
	}

    public void setStackTrace(StackTraceElement[] trace) {
        if (trace != null && trace.length > 0) {
            StringBuffer bf = new StringBuffer();
            for (int i=0; i<trace.length; i++) {
                bf.append(trace[i].toString()).append("\n");
            }
            stackTraceTag = new GcStackTraceTag(bf.toString());
        }
    }
    

	@Override
	public String toTagString() throws Exception {
		return this.classe + "-"+
        this.mensagem + "-"+
        this.excecao ;
	}

	@Override
	public String getTAG() {
		return TAG;
	}

}

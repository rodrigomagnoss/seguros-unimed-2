package br.com.segurosunimed.gestaocarteiras.commons.logger.tag;

import br.com.segurosunimed.gestaocarteiras.commons.mail.util.GcMailMessageStringUtil;

/**
 * Classe para rotulos utilizados nas mensagens de log referentes a pilha de rastreamento da excecao no log
 *  
 * @author Rodrigo Magno
 *
 */
public class GcStackTraceTag implements GcTagFacade {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "stackTrace";
    private static final String[] tagProps = {"mensagem"};
    
    private String mensagem;
    
    public GcStackTraceTag() {
    }
    
    public GcStackTraceTag(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public String getTAG() {
        return TAG;
    }
    
    public String toTagString() throws Exception {
        return GcMailMessageStringUtil.getInstance()
                .toTagString(this, tagProps);
    }

    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}

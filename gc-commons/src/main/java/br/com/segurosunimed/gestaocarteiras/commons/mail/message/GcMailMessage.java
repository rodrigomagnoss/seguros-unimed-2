package br.com.segurosunimed.gestaocarteiras.commons.mail.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcAplicacaoTag;
import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcExcecaoTag;
import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcTagFacade;

/**
 * Classe para compor mensagem a ser enviada por email. 
 */
public class GcMailMessage implements GcMailMessageFacade {

	private static final long serialVersionUID = 1L;
	
	private Date data;
	private String sender;
	private String replyTo;
	
	private String fromName;
	private String fromAddress;
	
	private String toName;
	private String toAddress;
	
	private String toCcAddress;
	private String toBccAddress;
	private String subject;
	private String body;
    private boolean enviarEmail = true;

    private List<GcTagFacade> tags;
    private GcMailMessage mailMessage;
    private GcAplicacaoTag aplicacao;
    private GcExcecaoTag excecao;

	private String mimeType = CONTEUDO_HTML;
	private int prioridade = PRIORIDADE_NORMAL;
	private String formato = FORMATO_HTML;
	
	private String modoExecucao = "";
	private String mensagem;
	private String objeto;
	
	
	private Set<GcMailMessageFacade> anexos;
	
	private byte[] arquivo;

	private boolean mensagemEnviada = true;
	
	public void adicionaAnexo(GcMailMessageFacade anexo) {
		if (anexos == null)
			anexos = new HashSet<GcMailMessageFacade>();

		anexos.add(anexo);
	}

	public String getBody() {
		return body;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public String getMimeType() {
		return mimeType;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public String getSubject() {
		return subject;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setBody(String string) {
		body = string;
	}

	public void setBody(byte[] bs) {
		arquivo = bs;
	}

	public void setFromAddress(String string) {
		fromAddress = string;
	}

	public void setMimeType(String string) {
		mimeType = string;
	}

	public void setPrioridade(int i) {
		prioridade = i;
	}

	public void setSubject(String string) {
		subject = string;
	}

	public void setToAddress(String string) {
		toAddress = string;
	}
	
	public Set<GcMailMessageFacade> getAnexos() {
		return anexos;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setAnexos(Set<GcMailMessageFacade> set) {
		anexos = set;
	}

	public void setArquivo(byte[] bs) {
		arquivo = bs;
	}

	public void setToCcAddress(String newToCc) {
		toCcAddress = newToCc;
	}

	public String getToCcAddress() {
		return toCcAddress;
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

	public String getToBccAddress() {
		return toBccAddress;
	}

	public void setToBccAddress(String toBccAddress) {
		this.toBccAddress = toBccAddress;
	}

//	public boolean isMensagemBruta() {
//		return mensagemBruta;
//	}
//
//	public void setMensagemBruta(boolean mensagemBruta) {
//		this.mensagemBruta = mensagemBruta;
//	}
//
//	public String getConteudoMensagemBruta() {
//		return conteudoMensagemBruta;
//	}
//
//	public void setConteudoMensagemBruta(String conteudoMensagemBruta) {
//		this.conteudoMensagemBruta = conteudoMensagemBruta;
//	}
//
//	public int getLevelMensagemBruta() {
//		return levelMensagemBruta;
//	}
//
//	public void setLevelMensagemBruta(int levelMensagemBruta) {
//		this.levelMensagemBruta = levelMensagemBruta;
//	}
//
	public GcMailMessage getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(GcMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public boolean isEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public GcAplicacaoTag getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(GcAplicacaoTag aplicacao) {
		this.aplicacao = aplicacao;
	}

	public GcExcecaoTag getExcecao() {
		return excecao;
	}

	public void setExcecao(GcExcecaoTag excecao) {
		this.excecao = excecao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<GcTagFacade> getTags() {
		return tags;
	}

	public void setTags(List<GcTagFacade> tags) {
		this.tags = tags;
	}

	public void setFormato(String string)
	{
		formato = string;
	}

	public String getFormato()
	{
		return formato;
	}
	
    /**
     * Adiciona a tag a lista de tags.
     * 
     * @param tag
     */
    public GcMailMessage addTag(GcTagFacade tag) {
        if (tags == null) {
            tags = new ArrayList<GcTagFacade>(2);
        }
        if (tag != null) {
            tags.add(tag);
        }
        return this;
    }
    
	/**
	 * Verifica se há anexos e os adiciona 
	 * 
	 */
	public void montaAnexos(GcMailMessageFacade mailMessage, GcMailMessageFacade mail )
	{
		Set anexos = mailMessage.getAnexos();
		if ( anexos != null)
		{
			for (Iterator i=anexos.iterator(); i.hasNext();)
			{
				GcMailMessageFacade anexo = (GcMailMessageFacade) i.next();
				
				anexo.setSubject(mailMessage.getSubject());
				anexo.setMimeType(mailMessage.getMimeType());
				
				if (mailMessage.getArquivo() != null)
					anexo.setBody(mailMessage.getArquivo());
				else
					anexo.setBody(mailMessage.getBody());
				
				mail.adicionaAnexo(anexo);
			}
		}
	}

	@Override
	public String getCategoria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCategoria(String categoria) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTag(GcTagFacade tag) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getMensagemOriginal() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void setMensagemOriginal(String mensagem) {
//		// TODO Auto-generated method stub
//	}

	public String getFromName() {
		return StringUtils.isNotBlank(fromName) ? fromName : getFromAddress();
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return StringUtils.isNotBlank(toName) ? toName : getToAddress();
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
	
	public String toMailMessageJson(){
		
		String body = 	"{" +
				"\"email\":{" +
					"\"toName\": \""+getToName()+"\","+
					"\"toMail\": \""+getToAddress()+"\","+
					"\"fromName\": \""+getFromName()+"\","+
					"\"fromMail\": \""+getFromAddress()+"\","+
					"\"subject\": \""+getSubject()+"\","+
					"\"body\": \""+getBody()+"\""+
				"}"+
		"}";

		return body;
	}

	public boolean isMensagemEnviada() {
		return mensagemEnviada;
	}

	public void setMensagemEnviada(boolean mensagemEnviada) {
		this.mensagemEnviada = mensagemEnviada;
	}

	public String getModoExecucao() {
		return modoExecucao;
	}

	public void setModoExecucao(String modoExecucao) {
		this.modoExecucao = modoExecucao;
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

}
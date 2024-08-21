package br.com.segurosunimed.gestaocarteiras.commons.mail.message;

import java.util.Set;

import br.com.segurosunimed.gestaocarteiras.commons.logger.tag.GcTagFacade;

/**
 * Interface para classes de mensagem para envio por email.
 *  
 */
public interface GcMailMessageFacade extends java.io.Serializable {
	
	public static final int PRIORIDADE_BAIXA = -1; 
	public static final int PRIORIDADE_NORMAL = 0; 
	public static final int PRIORIDADE_ALTA = 1;
	public static final String FORMATO_HTML = "H";
	public static final String FORMATO_TEXTO = "T";
	public static final String CONTEUDO_HTML = "text/html"; 
	public static final String CONTEUDO_TEXTO = "text/plain"; 

	public static final String CATEGORIA_MAIL = "MAIL";
	
	//Métodos para envelope do Email
	public void setSender(String sender);
	public String getSender();

	public void setReplyTo(String replyTo);
	public String getReplyTo();

	public void 	setFromAddress(String newFromAddress);
	public String 	getFromAddress();
	
	public String 	getToAddress();
	public void 	setToAddress(String newToAddress);
	
	public void setToCcAddress(String newToCc);
	
	public String getToCcAddress();

	public void setToBccAddress(String toBcc);

	public String getToBccAddress();
	
	public void 	setSubject(String newSubject);
	public String 	getSubject();
	
	public void 	setBody(String newBody);
	public String 	getBody();

	public void setMimeType(String newMimeType);

	public String getMimeType();

	
	//Métodos para controle da Mensagem
	public String getCategoria();
	public void setCategoria(String categoria);

	public String getTag();
	public void setTag(GcTagFacade tag);

	public String getMensagemOriginal();
//	public void setMensagemOriginal(String mensagem);

	/**
	 * Configura o n�vel de prioridade do email.
	 *  
	 * @param newPrioridade - Um dos valores: <ul>
	 * 					<li>{@link GcMailMessageFacade#PRIORIDADE_ALTA}</li>
	 * 					<li>{@link GcMailMessageFacade#PRIORIDADE_NORMAL}</li>
	 * 					<li>{@link GcMailMessageFacade#PRIORIDADE_BAIXA}</li></ul>
	 */
	public void setPrioridade(int newPrioridade);


	public int getPrioridade();
	
	/**
	 * Adiciona anexos a mensagem de email.
	 * <p>
	 * Para enviar anexos inclua tantos quantos forem necessários
	 * ao Set anexos. O assunto será o  nome do anexo. Se o anexo for binário,
	 * informar setBody(byte[] b).
	 */
	public void adicionaAnexo(GcMailMessageFacade anexo);
	
	/**
	 * Adicina conteúdo binário.
	 * 
	 */
	public void setBody(byte[] bs);
	
	public void setAnexos(Set<GcMailMessageFacade> newAnexos);
	
	public Set<GcMailMessageFacade> getAnexos();
	
	public void setArquivo(byte[] bs);

	public byte[] getArquivo();
		
}
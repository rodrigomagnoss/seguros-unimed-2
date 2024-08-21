package br.com.segurosunimed.gestaocarteiras.commons.exception;

import org.slf4j.Logger;

/**
 * eCompany 5.5 - Classe de Exce��o para n�o enviar stack trace no logo
 * 
 * @author Rodrigo Magno
  * @since 15/05/2013 - ECP 6.1
 */
public class GcNoStackTraceException extends Exception {

	private static final long serialVersionUID = 1L;
	public GcNoStackTraceException(){
		super();
	}

	public GcNoStackTraceException(Throwable e){
//		setMessageArgs(new Object[]{e});
//		setCausaRaiz(e);
	}

	public GcNoStackTraceException(String mensagem, Throwable e, Logger logger){
//		super(mensagem, e, logger);
	}

	public GcNoStackTraceException(String mensagem){
		super(mensagem);
	}
	
	public GcNoStackTraceException(Exception plcEx){
//		super(plcEx.getMessageKey());
	}

	public GcNoStackTraceException(String messageKeyLoc,Object[] messageArgsLoc)   {
//		setMessageKey(messageKeyLoc);
//		setMessageArgs(messageArgsLoc);
	}
		
}


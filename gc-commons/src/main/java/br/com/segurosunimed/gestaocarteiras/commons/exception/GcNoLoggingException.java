package br.com.segurosunimed.gestaocarteiras.commons.exception;

/**
 * Classe para excecao nao propagada no appender
 * 
 * @author Rodrigo Magno
  * @since 19/05/2020
 */
public class GcNoLoggingException extends Exception {

	private static final long serialVersionUID = 1L;
	public GcNoLoggingException(){
		super();
	}

	public GcNoLoggingException(Throwable e){
		super(e);
//		setMessageArgs(new Object[]{e});
//		setCausaRaiz(e);
	}

//	public GcNoLoggingException(String mensagem, Throwable e, Logger logger){
//		super(mensagem, e, logger);
//	}

	public GcNoLoggingException(String mensagem){
		super(mensagem);
	}
	
	public GcNoLoggingException(Exception ex){
		super(ex);
	}

}


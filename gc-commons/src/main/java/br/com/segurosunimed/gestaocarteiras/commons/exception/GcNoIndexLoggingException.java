package br.com.segurosunimed.gestaocarteiras.commons.exception;

/**
 * Classe para excecao nao propagada no appender para indexacao de logs
 * 
 * @author Rodrigo Magno
  * @since 22/05/2020
 */
public class GcNoIndexLoggingException extends Exception {

	private static final long serialVersionUID = 1L;
	public GcNoIndexLoggingException(){
		super();
	}

	public GcNoIndexLoggingException(Throwable e){
		super(e);
	}

	public GcNoIndexLoggingException(String mensagem){
		super(mensagem);
	}
	
	public GcNoIndexLoggingException(Exception ex){
		super(ex);
	}

}


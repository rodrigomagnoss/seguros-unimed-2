package br.com.segurosunimed.gestaocarteiras.commons.exception;

/**
 * Classe para excecao nao propagada no appender para envio de email de erro
 * 
 * @author Rodrigo Magno
  * @since 22/05/2020
 */
public class GcNoMailLoggingException extends Exception {

	private static final long serialVersionUID = 1L;
	public GcNoMailLoggingException(){
		super();
	}

	public GcNoMailLoggingException(Throwable e){
		super(e);
	}

	public GcNoMailLoggingException(String mensagem){
		super(mensagem);
	}
	
	public GcNoMailLoggingException(Exception ex){
		super(ex);
	}

}


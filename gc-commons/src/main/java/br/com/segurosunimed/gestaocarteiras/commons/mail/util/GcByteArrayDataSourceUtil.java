package br.com.segurosunimed.gestaocarteiras.commons.mail.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

/**
 * DataSource para uso com DataHandler para permitir envio de anMensagem original de diversos
 * mime-types diversos.<br>
 * 
 * Sem utilizar a implementa��o de um DataSource n�o � poss�vel enviar anexo do
 * tipo pdf, por exemplo.
 * 
 */
public class GcByteArrayDataSourceUtil implements DataSource {
	
	private byte[] b;
	private String nome;
	private String contentType;

	public GcByteArrayDataSourceUtil(byte[] b, String contentType, String nome) {
		this.b = (b != null ? b : new byte[0]);
		this.contentType = contentType;
		this.nome = nome;
	}

	public GcByteArrayDataSourceUtil(byte[] b, String contentType) {
		this(b, contentType, null);
	}

	
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(b);
	}

	/**
	 * Esse metodo retorna um OutputStream que nao eh utilizado internamente. Se
	 * sua rotina necessitar ler esse output (rotinas de recebimento de email,
	 * normalmente) voce deve extender essa classe e sobrescrever esse metodo.
	 */
	public OutputStream getOutputStream() throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		return bout;
	}

	public String getContentType() {
		return contentType;
	}

	public String getName() {
		return nome;
	}

}

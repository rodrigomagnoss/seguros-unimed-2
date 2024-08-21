package br.com.segurosunimed.gestaocarteiras.commons.mail.message;

import java.io.Serializable;

import br.com.segurosunimed.gestaocarteiras.commons.constants.GcConstantes;


/**
 * Classe ancestral para mensagens de logging de historico.
 */
public class GcHistMsgLog extends GcMailMessageLogging implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public GcHistMsgLog(String mensagem){
		//setTipoObjeto(GcConstantes.TP_MSG_EVT);
		setBody(mensagem);
	}
	
	@Override
	public String toString() {
		
		return getBody();
	}
}
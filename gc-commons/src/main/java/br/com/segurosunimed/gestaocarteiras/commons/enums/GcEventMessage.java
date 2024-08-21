package br.com.segurosunimed.gestaocarteiras.commons.enums;

public enum GcEventMessage {

	LOGON("Logon Executado"), 
	GENERIC_ERROR("Erro"), 
	CREATE_ENTITY("#ENTITY# criado"), 
	UPDATE_ENTITY("#ENTITY# atualizado"), 
	DELETE_ENTITY("#ENTITY# excluido")
	;

	private String value;

	public String value(){
		return this.value;
	}

	private GcEventMessage (String value){
		this.value = value;
	}


}

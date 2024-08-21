package br.com.segurosunimed.gestaocarteiras.commons.enums;

public enum GcLogType {
	
	FATAL ("FATAL"),
	ERROR ("ERROR"),
	WARN ("WARN"),
	INFO ("INFO"),
	DEBUG ("DEBUG");
	
	
	private String tipo;

    public String nome(){
        return this.toString();
    }

    public String tipo(){
        return this.tipo;
    }
    
    private GcLogType (String tipo){
        this.tipo = tipo;
    }

}

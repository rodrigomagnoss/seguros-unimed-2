package br.com.segurosunimed.gestaocarteiras.commons.enums;

/**
 * Classe de enumeracao para tipos de logs enviados para a API backend.
 */
public enum GcEnumTipoLog {

	FATAL ("crit"),
	ERROR ("error"),
	WARN ("warnng"),
	INFO ("info"),
	DEBUG ("debug");
	
	
	private String tipo;

    public String nome(){
        return this.toString();
    }

    public String tipo(){
        return this.tipo;
    }
    
    private GcEnumTipoLog (String tipo){
        this.tipo = tipo;
    }


}
package br.com.segurosunimed.gestaocarteiras.commons.enums;

/**
 * Classe de enumeracao para tipos de arquivos
 */
public enum GcFileType {

	PDF("application/pdf"),  	
	XML("text/xml"),		
	TXT("text/plain"),		
	JSON("application/json");	
	
	private String tipo;
	 
    public String mimetype(){
        return this.tipo;
    }
    
    private GcFileType (String tipo){
        this.tipo = tipo;
    }


}
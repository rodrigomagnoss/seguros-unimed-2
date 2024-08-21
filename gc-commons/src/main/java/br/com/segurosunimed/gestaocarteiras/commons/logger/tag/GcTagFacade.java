package br.com.segurosunimed.gestaocarteiras.commons.logger.tag;

import java.io.Serializable;

/**
 * Classe de interface para rotulos utilizados nas mensagens de log
 *  
 * @author Rodrigo Magno
 *
 */
public interface GcTagFacade extends Serializable {
    
    public String getTAG();
    
    public String toTagString() throws Exception;
    
}

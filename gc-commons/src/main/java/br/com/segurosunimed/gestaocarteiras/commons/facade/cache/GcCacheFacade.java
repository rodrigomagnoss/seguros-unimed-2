package br.com.segurosunimed.gestaocarteiras.commons.facade.cache;

import java.util.Map;

import br.com.segurosunimed.gestaocarteiras.commons.facade.contexto.GcContextoFacade;
/**
 * Classe facade para cache de parâmetros da aplicação
 * 
 * @since 30/01/2020
 * @author Rodrigo Magno
 * 
 */
public interface GcCacheFacade extends GcContextoFacade{

	public Map<String, Object> getMapaCacheContexto();
	
	public void setMapaCacheContexto(Map<String, Object> mapaCacheContexto);


}

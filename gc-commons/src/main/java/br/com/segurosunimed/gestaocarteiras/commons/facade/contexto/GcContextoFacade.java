package br.com.segurosunimed.gestaocarteiras.commons.facade.contexto;

import java.util.Map;
/**
 * Classe facade para contextos da aplicação
 * 
 * @since 30/01/2020
 * @author Rodrigo Magno
 * 
 */
public interface GcContextoFacade {

	public Map<String, Object> recuperarMapaContexto();
	
	public void setMapaContexto(Map<String, Object> mapaContexto);

//	public void inicializarContexto();
	
	public void setAttribute(String attributeKey, Object attributeValue);
	
	public Object getAttribute(String attributeKey);

	public Map<String, Object> recuperarMapaContexto(String nomeServico);
	
	public Map<String, Object> recuperarMapaContexto(String nomeServicoPai, String nomeServico);
	
	public void setMapaContexto(String nomeServico, Map<String, Object> mapaContexto);

	
}

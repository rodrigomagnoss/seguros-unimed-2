package br.com.segurosunimed.gestaocarteiras.commons.enums;

/**
 * Classe de enumeracao para motivos de ignorar testes para cobertuda de testes
 */
public enum GcEnumMotivoIgnorarTeste {

	SIMPLES,  		//Teste unitário não é necessário pois o método é muito simples
	CHAMADA_EXTERNA,//Teste unitário deste método já foi implementado em uma chamada dele externamente
	CHAMADA_LOCAL,	//Teste unitário deste método já foi implementado em uma chamada dele localmente
	DESCENDENTE,	//Teste unitário deste método já foi implementado no ancestral ou é um método template 
	DELEGATE,		//Teste unitário deste método já foi implementado nos métodos que ele delega a execução
	ANCESTRAL;		//Teste unitário deste método já foi implementado no descendente

}
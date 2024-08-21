package br.com.segurosunimed.gestaocarteiras.commons.enums;

/**
 * Classe de enumeracao para acoes de cobertuda de testes
 */
public enum GcEnumAcaoTeste {

	DETALHAR,  	//Testes unitários serão implementados após quebra do código do método complexo
	ADIAR,		//Testes unitários serão implementados posteriormente
	AVALIAR;	//Testes unitários serão implementados após avaliação do código do método
	
	public boolean ehDetalhar(){
		return this.compareTo(DETALHAR) == 0;
	}

	public boolean ehAdiar(){
		return this.compareTo(ADIAR) == 0;
	}

	public boolean ehAvaliar(){
		return this.compareTo(AVALIAR) == 0;
	}

}
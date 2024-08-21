package br.com.segurosunimed.gestaocarteiras.teste.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.segurosunimed.gestaocarteiras.commons.teste.base.GcSuiteBaseTeste;
import br.com.segurosunimed.gestaocarteiras.teste.suite.caso.GcCommonsCoberturaCasoTeste;
import br.com.segurosunimed.gestaocarteiras.teste.suite.plano.GcPlanoTeste;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GcCommonsCoberturaCasoTeste.class,
	GcPlanoTeste.class
})

/**
 * Classe suite para verificacao de cobertura de testes do módulo Commons
 * 
 * @author Rodrigo Magno
 * @since  19/06/2024
 */
public class GcCommonsSuiteBaseTeste extends GcSuiteBaseTeste {

	
}
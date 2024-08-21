package br.com.segurosunimed.gestaocarteiras.teste.suite.caso;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.segurosunimed.gestaocarteiras.commons.teste.base.GcCoberturaBaseTeste;

public class GcCommonsCoberturaCasoTeste extends GcCoberturaBaseTeste {

	static Logger logT = Logger.getLogger("teste");

	static protected String[] pacotesCore = {
			"br.com.segurosunimed.gestaocarteiras.commons.config"	
		};


	@Test
	public void verificarCoberturaTestes() {

		boolean todosMetodosCobertos = true;
		
		logT.info("INI COBERTURA DE TESTES");

		todosMetodosCobertos = verificarCoberturaTestes(pacotesCore);

		logT.info("FIM COBERTURA DE TESTES");

		assertTrue(todosMetodosCobertos);

	}


	@Before
	public void iniciandoCasoTeste() {

		logT.info("INI CASO");
		
	}

	@After
	public void finalizandoCasoTeste() {

		logT.info("FIM CASO");
		
	}

	
}
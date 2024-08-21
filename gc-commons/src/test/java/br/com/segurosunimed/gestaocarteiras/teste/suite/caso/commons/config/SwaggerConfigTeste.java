package br.com.segurosunimed.gestaocarteiras.teste.suite.caso.commons.config;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.segurosunimed.gestaocarteiras.commons.config.SwaggerConfig;
import br.com.segurosunimed.gestaocarteiras.teste.suite.caso.GcCommonsCasoTeste;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfigTeste extends GcCommonsCasoTeste {

	@BeforeClass
	public static void iniciandoTeste() {

	}

	@AfterClass
	public static void finalizandoTeste() {
		
	}

	@Test
	public void testeApi() {

		SwaggerConfig sc = new SwaggerConfig();
		Docket api = sc.api();
		
		assertNaoNulo(api);
	}


	
}
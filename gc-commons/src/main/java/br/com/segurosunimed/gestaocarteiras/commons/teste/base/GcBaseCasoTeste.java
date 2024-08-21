package br.com.segurosunimed.gestaocarteiras.commons.teste.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;

public class GcBaseCasoTeste extends GcBaseTeste{

	@Ignore
	protected void assertVerdadeiro(boolean condicao){
		assertTrue(condicao);
	}

	@Ignore
	protected void assertFalso(boolean condicao){
		assertFalse(condicao);
	}

	@Ignore
	protected void assertNulo(Object objeto){
		assertNull(objeto);
	}

	@Ignore
	protected void assertNulo(String mensagem, Object objeto){
		assertNull(mensagem, objeto);
	}

	@Ignore
	protected void assertNaoNulo(Object objeto){
		assertNotNull(objeto);
	}

	@Ignore
	protected void assertNaoNulo(String mensagem, Object objeto){
		assertNotNull(mensagem, objeto);
	}

	@Ignore
	protected void assertNaoNuloENaoVazio(String string){
		
		assertVerdadeiro(StringUtils.isNotBlank(string));
		
	}

	@Ignore
	protected void assertIgual(Object objEsperado, Object objAtual){
		
		assertEquals(objEsperado, objAtual);
		
	}

	@Ignore
	protected void assertDiferente(Object objEsperado, Object objAtual){
		
		assertNotEquals(objEsperado, objAtual);
		
	}

	@Ignore
	protected void falha(){
		
		fail();
		
		
	}

}
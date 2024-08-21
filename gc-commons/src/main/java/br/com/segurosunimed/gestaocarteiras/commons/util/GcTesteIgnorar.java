package br.com.segurosunimed.gestaocarteiras.commons.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEnumMotivoIgnorarTeste;

/**
 * Classe de anotacao para informar motivo de ignorar teste automatizado para cobertura 
 */
@Target({ ElementType.TYPE_USE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GcTesteIgnorar {

	GcEnumMotivoIgnorarTeste motivo() default GcEnumMotivoIgnorarTeste.SIMPLES;
	
}

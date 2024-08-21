package br.com.segurosunimed.gestaocarteiras.commons.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Informa que a propriedade deve ser ignorada ao fazer comparação de propriedades do artefato. 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnorarAoComparar {
}
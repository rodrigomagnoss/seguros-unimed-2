package br.com.segurosunimed.gestaocarteiras.commons.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Informa que a propriedade deve ser ignorada ao importar propriedades para template produto. 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnorarAoImportar {
}
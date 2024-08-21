package br.com.segurosunimed.gestaocarteiras.commons.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEnumAcaoTeste;

/**
 * Classe de anotacao para informar acao para cobertura de testes automatizados 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GcTesteAcao {

    GcEnumAcaoTeste acao();
    
}

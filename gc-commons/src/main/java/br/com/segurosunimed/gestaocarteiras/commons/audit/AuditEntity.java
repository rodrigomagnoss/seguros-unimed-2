package br.com.segurosunimed.gestaocarteiras.commons.audit;

/*
 * Classe entidade para mapeamento dos campos de auditoria created_at, created_by, updated_at e updated_by
 */

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AuditEntity {

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "criado_por")
    private String criadoPor;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "atualizado_por")
    private String atualizadoPor;

    //Getters and setters omitted for brevity
}
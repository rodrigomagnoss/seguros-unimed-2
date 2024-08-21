package br.com.segurosunimed.gestaocarteiras.commons.audit.create;

/*
 * Classe entidade para mapeamento dos campos de auditoria created_at e created_by
 */
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CreateAuditEntity {

	@Column(name = "criado_em")
	private LocalDateTime criadoEm;

	@Column(name = "criado_por")
	private String criadoPor;

}
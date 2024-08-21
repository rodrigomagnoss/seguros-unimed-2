package br.com.segurosunimed.gestaocarteiras.commons.audit.create;

/*
 * Classe para interceptar e registrar de forma automatizada os campos de auditoria para criação
 */
import java.time.LocalDateTime;

import javax.persistence.PrePersist;

import br.com.segurosunimed.gestaocarteiras.commons.audit.AuditLoggedUser;

public class CreateAuditListener {

	@PrePersist
	public void setCreatedOn(CreateAuditable auditable) {
		CreateAuditEntity audit = auditable.getCreateAudit();

		if (audit == null) {
			audit = new CreateAuditEntity();
			auditable.setCreateAudit(audit);
		}

		audit.setCriadoEm(LocalDateTime.now());
		audit.setCriadoPor(AuditLoggedUser.getLoggedUserName());

	}

}
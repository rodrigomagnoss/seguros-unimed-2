package br.com.segurosunimed.gestaocarteiras.commons.audit;

/*
 * Classe para interceptar e registrar de forma automatizada os campos de auditoria para criação e atualização
 */

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditListener {
	
    @PrePersist
    public void setCreatedOn(Auditable auditable) {
        AuditEntity audit = auditable.getAudit();

        if(audit == null) {
            audit = new AuditEntity();
            auditable.setAudit(audit);
        }

        audit.setCriadoEm(LocalDateTime.now());
        audit.setCriadoPor(AuditLoggedUser.getLoggedUserName().trim());
        
        setUpdadtedOn(auditable);        
    }

    @PreUpdate
    public void setUpdadtedOn(Auditable auditable) {
        AuditEntity audit = auditable.getAudit();

        if(audit == null) {
            audit = new AuditEntity();
        }

        audit.setAtualizadoEm(LocalDateTime.now());
        audit.setAtualizadoPor(AuditLoggedUser.getLoggedUserName().trim());
    }
}
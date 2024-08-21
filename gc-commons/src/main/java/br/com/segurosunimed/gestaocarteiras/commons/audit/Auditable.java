package br.com.segurosunimed.gestaocarteiras.commons.audit;

/*
 * Interface para registro automatizado dos campos de AuditEntity
 */

public interface Auditable {

    AuditEntity getAudit();

    void setAudit(AuditEntity audit);
}
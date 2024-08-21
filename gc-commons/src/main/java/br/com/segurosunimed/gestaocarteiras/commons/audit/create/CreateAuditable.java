package br.com.segurosunimed.gestaocarteiras.commons.audit.create;

/*
 * Interface para registro automatizado dos campos de CreateAuditEntity
 */
public interface CreateAuditable {

    CreateAuditEntity getCreateAudit();

    void setCreateAudit(CreateAuditEntity createUudit);
}
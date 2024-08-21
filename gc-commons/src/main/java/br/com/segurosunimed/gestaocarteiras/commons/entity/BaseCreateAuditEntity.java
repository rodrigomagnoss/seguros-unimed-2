package br.com.segurosunimed.gestaocarteiras.commons.entity;


import br.com.segurosunimed.gestaocarteiras.commons.annotation.IgnorarAoCopiar;
import br.com.segurosunimed.gestaocarteiras.commons.audit.create.CreateAuditEntity;
import br.com.segurosunimed.gestaocarteiras.commons.audit.create.CreateAuditListener;
import br.com.segurosunimed.gestaocarteiras.commons.audit.create.CreateAuditable;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EntityListeners(CreateAuditListener.class)
public class BaseCreateAuditEntity extends BaseEntity implements CreateAuditable {
	
	@IgnorarAoCopiar
    @Embedded
    private CreateAuditEntity createAudit;
}

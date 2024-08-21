package br.com.segurosunimed.gestaocarteiras.commons.entity;

import br.com.segurosunimed.gestaocarteiras.commons.annotation.IgnorarAoCopiar;
import br.com.segurosunimed.gestaocarteiras.commons.audit.AuditEntity;
import br.com.segurosunimed.gestaocarteiras.commons.audit.AuditListener;
import br.com.segurosunimed.gestaocarteiras.commons.audit.Auditable;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EntityListeners(AuditListener.class)
public class BaseAuditEntity extends BaseEntity implements Auditable {
	
	@IgnorarAoCopiar
    @Embedded
    private AuditEntity audit;
}

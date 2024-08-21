package br.com.segurosunimed.gestaocarteiras.commons.hibernate.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
class AuditLogInterceptorCustomizer implements HibernatePropertiesCustomizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogInterceptorCustomizer.class);

    @Autowired
    AuditLogInterceptor auditLogInterceptor;

	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		try {
		      hibernateProperties.put("hibernate.session_factory.interceptor", auditLogInterceptor);
		} catch (Exception e) {
			LOGGER.warn("Nao foi possivel configurar AuditLogInterceptor.");
		}	
	}

}
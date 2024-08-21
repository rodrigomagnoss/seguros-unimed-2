package br.com.segurosunimed.gestaocarteiras.commons.hibernate.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
public class HibernateConfig implements HibernatePropertiesCustomizer {

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
//        hibernateProperties.put("hibernate.integrator_provider",
//                (IntegratorProvider) () -> Collections.singletonList(ReplicationEventListenerIntegrator.INSTANCE));
    	
    	
    }
}
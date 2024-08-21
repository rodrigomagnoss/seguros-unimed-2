package br.com.segurosunimed.gestaocarteiras.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEnumAcaoTeste;
import br.com.segurosunimed.gestaocarteiras.commons.enums.GcEnumMotivoIgnorarTeste;
import br.com.segurosunimed.gestaocarteiras.commons.util.GcTesteAcao;
import br.com.segurosunimed.gestaocarteiras.commons.util.GcTesteIgnorar;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	

    @Value("${gc.name}")
    private String gestaoName;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("br.com.segurosunimed.gestaocarteiras"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
					
	
	}
	
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(gestaoName)
				.description("APIs Gestão de Carteiras")
				.termsOfServiceUrl("http://gestaocarteiras.com.br")
				.version("2.0")
				.build();
	
	}	
	
}

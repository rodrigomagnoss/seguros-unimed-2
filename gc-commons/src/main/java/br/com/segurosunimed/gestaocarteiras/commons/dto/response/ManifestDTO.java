package br.com.segurosunimed.gestaocarteiras.commons.dto.response;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManifestDTO {
	
    private String projeto;
    private String version;
    private String datetime;
    private String organizacao;

}

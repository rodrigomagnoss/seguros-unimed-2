package br.com.segurosunimed.gestaocarteiras.commons.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ManifestDTO;
import br.com.segurosunimed.gestaocarteiras.commons.service.sys.ManifestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "REST Api para versão da dependência gestao_commons", tags = { "Sistema - Projeto APIs Commons" })

@RestController
public class CommonsVersionController {
	
	@Autowired
	ManifestService manifestService;
	
	@ApiOperation(value = "Recuperar versao do gestao-commons")
	@RequestMapping(value = "/api/v1/commons/version", method = RequestMethod.GET)
	public ResponseEntity<ManifestDTO> version() {

		ManifestDTO manifestDTO = new ManifestDTO();

		manifestDTO = manifestService.mostrarDadosManifesto();
		
		return new ResponseEntity<>(manifestDTO, HttpStatus.OK);
	}
	
}

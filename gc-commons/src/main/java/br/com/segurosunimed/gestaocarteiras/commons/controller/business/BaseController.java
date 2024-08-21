package br.com.segurosunimed.gestaocarteiras.commons.controller.business;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import br.com.segurosunimed.gestaocarteiras.commons.dto.BaseDTO;
import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import br.com.segurosunimed.gestaocarteiras.commons.exception.BusinessException;
import br.com.segurosunimed.gestaocarteiras.commons.service.business.BaseService;
import io.swagger.annotations.Api;

@RestController
public class BaseController  {
	
	protected BaseDTO findById(Long id) {
		
		return getService().find(id);
	}

	protected BaseDTO findList() {
		
		return (BaseDTO) getService().findList();
	}

	protected List<? extends BaseDTO> listAll() {
		
		return getService().list();
	}

	protected BaseDTO filter(BaseDTO baseDTO) {
		
		BaseDTO baseResDTO = (BaseDTO) getService().filter(baseDTO);
		
		return baseResDTO;
	}

	protected BaseDTO create(BaseDTO baseDTO) {
		
		baseDTO = getService().create(baseDTO);
		
		return baseDTO;
	}

	protected BaseDTO edit(BaseDTO baseDTO) {
		
		baseDTO = getService().edit(baseDTO);
		
		return baseDTO;
	}

	protected BaseDTO delete(BaseDTO baseDTO) {
		
		baseDTO = getService().delete(baseDTO);
		
		return baseDTO;
	}
	
	protected BaseService getService() {
		
		ErrorDTO errorDTO = new ErrorDTO("service_null_error", "Metodo getService nao implementado no controller.");
		throw new BusinessException(errorDTO);
		
	}
}

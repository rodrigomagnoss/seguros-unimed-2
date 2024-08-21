package br.com.segurosunimed.gestaocarteiras.commons.converter;

import java.util.List;

import br.com.segurosunimed.gestaocarteiras.commons.dto.BaseDTO;
import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import br.com.segurosunimed.gestaocarteiras.commons.entity.BaseEntity;
import br.com.segurosunimed.gestaocarteiras.commons.exception.BusinessException;

public class BaseConverter implements IBaseConverter{

	/* Converters To DTO */
	
	@Override
	public BaseDTO _toDTOFind(BaseEntity baseEntity) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toDTOFind nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public BaseDTO _toDTOCreate(BaseEntity baseEntity) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toDTOCreate nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public BaseDTO _toDTOUpdate(BaseEntity baseEntity) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toDTOUpdate nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public BaseDTO _toDTODelete(BaseEntity baseEntity) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toDTODelete nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public List<? extends BaseDTO> _toDTOList(List<? extends BaseEntity> lstBaseEntity) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo toDTOList nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public BaseDTO _toDTOFindList(List<? extends BaseEntity> lstBaseEntity) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toDTOFindList nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public BaseDTO _toDTOFilterList(List<? extends BaseDTO> listBaseDTO) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toDTOFilterList nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	/* Converters To Entity */

	@Override
	public BaseEntity _toEntityCreate(BaseDTO baseDTO) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toEntityCreate nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public BaseEntity _toEntityUpdateMaster(BaseEntity baseEntityRetriveDB, BaseDTO baseDTO) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toEntityUpdateMaster nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public BaseEntity _toEntityUpdateDetail(BaseDTO baseDTO) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toEntityUpdateDetail nao implementado no converter");
		throw new BusinessException(errorDTO);
	}

	@Override
	public List<? extends BaseEntity> _toEntityList(List<? extends BaseDTO> lstDTO) {
		ErrorDTO errorDTO = new ErrorDTO("converter_notfound_error", "Metodo _toEntityList nao implementado no converter");
		throw new BusinessException(errorDTO);
	}
	
	public String getNomeConverter() {
		
		return this.getClass().getCanonicalName();
		
	}

}

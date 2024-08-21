package br.com.segurosunimed.gestaocarteiras.commons.converter;

import java.util.List;

import br.com.segurosunimed.gestaocarteiras.commons.dto.BaseDTO;
import br.com.segurosunimed.gestaocarteiras.commons.entity.BaseEntity;

public interface IBaseConverter {

	/* CONVERTER - DTO */
	
    public BaseDTO _toDTOFind(BaseEntity baseEntity);

    public BaseDTO _toDTOCreate(BaseEntity baseEntity);

    public BaseDTO _toDTOUpdate(BaseEntity baseEntity);

    public BaseDTO _toDTODelete(BaseEntity baseEntity);
    
	public List<? extends BaseDTO> _toDTOList(List<? extends BaseEntity> lstBaseEntity);
	
	public BaseDTO _toDTOFindList(List<? extends BaseEntity> lstBaseEntity);
	
	public BaseDTO _toDTOFilterList(List<? extends BaseDTO> listBaseDTO);

	/* CONVERTER - ENTITY */

    public BaseEntity _toEntityCreate(BaseDTO baseDTO);

    public BaseEntity _toEntityUpdateMaster(BaseEntity baseEntityRetriveDB, BaseDTO baseDTO);

    public BaseEntity _toEntityUpdateDetail(BaseDTO baseDTO);

	public List<? extends BaseEntity> _toEntityList(List<? extends BaseDTO> lstDTO);
	
}

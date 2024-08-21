package br.com.segurosunimed.gestaocarteiras.commons.service.business;

import java.util.List;
import java.util.Optional;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import br.com.segurosunimed.gestaocarteiras.commons.converter.BaseConverter;
import br.com.segurosunimed.gestaocarteiras.commons.dto.BaseDTO;
import br.com.segurosunimed.gestaocarteiras.commons.dto.response.ErrorDTO;
import br.com.segurosunimed.gestaocarteiras.commons.entity.BaseEntity;
import br.com.segurosunimed.gestaocarteiras.commons.exception.BusinessException;
import br.com.segurosunimed.gestaocarteiras.commons.repository.BaseRepository;
import javax.transaction.Transactional;

/**
 * Classe ancestral para fluxos de servico
 * @author Rodrigo Magno
 * @since 29/01/2022 
 */
@Service
public class BaseService {

	
	/**  PUBLICOS - CONTROLLERS **/

	/**
	 * Método para recuperação de entidade referente à classe master pelo id com DTO
	 * @param id Identificador da classe master
	 * @return Objeto DTO da classe master recuperada
	 */
	public BaseDTO find(Long id) {
		
		BaseEntity baseEntity = findById(id);
		
		BaseDTO baseDTO = toDTOFind(baseEntity);
		
		return baseDTO;
		
	}

	/**
	 * Método para recuperação de lista todos as entidades referentes à classe master
	 * @return Lista de objetos DTO da classe master recuperada
	 */
	public List<? extends BaseDTO> list() {

		List<? extends BaseEntity> lstBaseEntity =   (List<? extends BaseEntity>) listAll();
		
		List<? extends BaseDTO> lstBaseDTO = toDTOList(lstBaseEntity);
		
		return lstBaseDTO;
	}

	/**
	 * Método para recuperação de entidade contendo lista referente à classe master
	 * @return Objeto DTO da classe master com lista de todas as entidades master
	 */
	public BaseDTO findList() {

		List<? extends BaseEntity> lstBaseEntity =   (List<? extends BaseEntity>) listAll();
		
		BaseDTO baseDTO = toDTOFindList(lstBaseEntity);
		
		return baseDTO;
	}

	/**
	 * Metodo para listar entidades master com filtro
	 * @param baseDTO Objeto DTO contendo os dados para edição
	 * @return Objeto DTO com lista dados filtrados
	 */
	public  BaseDTO filter(BaseDTO baseDTO) {

		List<? extends BaseDTO> lstBaseDTO =   (List<? extends BaseDTO>) _findByFilter(baseDTO);
		
		BaseDTO baseResDTO = getConverter()._toDTOFilterList(lstBaseDTO);
		
		return baseResDTO;
	}

	/**
	 * Metodo para edição de entidade master
	 * @param baseDTO Objeto DTO contendo os dados para edição
	 * @return Objeto DTO com dados editados
	 */
	public BaseDTO edit(BaseDTO baseDTO) {

		BaseEntity baseEntity = _retriveEntityUpdate(baseDTO);

		baseEntity = getConverter()._toEntityUpdateMaster(baseEntity, baseDTO);

		baseEntity = update(baseEntity);
		
		baseDTO = getConverter()._toDTOUpdate(baseEntity);

		return baseDTO;
	}

	/**
	 * Metodo para gravação de nova entidade master
	 * @param baseDTO Objeto DTO contendo os dados para gravação
	 * @return Objeto DTO com dados gravados
	 */
	public BaseDTO create(BaseDTO baseDTO) {

		BaseEntity baseEntity = getConverter()._toEntityCreate(baseDTO);
		
		baseEntity = insert(baseEntity);

		baseDTO = getConverter()._toDTOCreate(baseEntity);

		return baseDTO;
	}

	/**
	 * Metodo para exclusao de entidade master
	 * @param baseDTO Objeto DTO contendo os dados para exclusão
	 * @return Objeto DTO com dados excluidos
	 */
	public  BaseDTO delete(BaseDTO baseDTO) {

		BaseEntity baseEntity = _retriveEntityUpdate(baseDTO);

		baseEntity = delete(baseEntity);
		
		baseDTO = getConverter()._toDTODelete(baseEntity);

		return baseDTO;
	}

	/**  PROTEGIDOS - SERVICES - CONVERTERS **/
	

	/**
	 * Metodo para recuperação de entidade para update
	 * @param baseDTO Objeto DTO contendo os dados para recuperação entidade
	 * @return Entidade recuperada
	 */
	@Transactional
	protected BaseEntity _retriveEntityUpdate(BaseDTO baseDTO) {
	
		ErrorDTO errorDTO = new ErrorDTO("service.error", "Metodo _retriveEntityUpdate nao implementado na classe service");
		throw new BusinessException(errorDTO);
		
	}
	
	protected List<? extends BaseDTO> _findByFilter(BaseDTO baseDTO) {

		ErrorDTO errorDTO = new ErrorDTO("service.error", "Metodo _findByFilter nao implementado na classe service");
		throw new BusinessException(errorDTO);
	}

	
	/**
	 * Metodo para converter lista de Objetos DTO para lista de objetos de entidade
	 * @param lstDTO Lista de objetos DTO para conversão
	 * @return Lista de objetos entidade convertidas
	 */
	protected List<? extends BaseEntity> _toEntityList(List<? extends BaseDTO> lstDTO) {

		List<? extends BaseEntity> lstBaseEntity = getConverter()._toEntityList(lstDTO);
		
		return lstBaseEntity;
	}

	/**
	 * Metodo para converter entidade recuperada em objeto DTO
	 * @param baseEntity Lista de objetos de entidade para conversão
	 * @return Objeto DTO convertido
	 */
	protected BaseDTO toDTOFind(BaseEntity baseEntity) {

		BaseDTO baseDTO = getConverter()._toDTOFind(baseEntity);
		
		return baseDTO;
	}
	
	/**
	 * Metodo para converter lista de entidade para lista de objetos DTO
	 * @param lstBaseEntity Lista de entidade para conversão
	 * @return Lista de objetos DTO convertidos
	 */
	protected List<? extends BaseDTO> toDTOList(List<? extends BaseEntity> lstBaseEntity) {

		List<? extends BaseDTO> listBaseDTO = getConverter()._toDTOList(lstBaseEntity);
		
		return listBaseDTO;
	}

	/**
	 * Metodo para converter lista de entidade para objeto DTO
	 * @param baseEntity Lista de objetos de entidade para conversão
	 * @return Objeto DTO com lista convertida
	 */
	protected BaseDTO toDTOFindList(List<? extends BaseEntity> lstBaseEntity) {

		BaseDTO baseDTO = getConverter()._toDTOFindList(lstBaseEntity);
		
		return baseDTO;
	}

	/**
	 * Metodo para converter lista de entidade filtrados para objeto DTO
	 * @param baseEntity Lista de objetos de entidade filtrados para conversão
	 * @return Objeto DTO com lista convertida
	 */
	protected BaseDTO toDTOFilterList(List<? extends BaseEntity> lstBaseEntity) {

		BaseDTO baseDTO = getConverter()._toDTOFindList(lstBaseEntity);
		
		return baseDTO;
	}

	/**  PROTEGIDOS - SERVICES - BUSSINESS **/

	protected BaseEntity findById(Long id) {
		
		BaseEntity baseEntity = $findById(id);
		
		return baseEntity;
		
	}

	protected List<? extends BaseEntity> listAll() {

		List<? extends BaseEntity> lstBaseEntity =   (List<? extends BaseEntity>) $listAll();
		
		return lstBaseEntity;
	}

	@Transactional
	protected BaseEntity update(BaseEntity baseEntity) {

		baseEntity = $update(baseEntity);
		
		return baseEntity;
	}

	@Transactional
	protected  BaseEntity insert(BaseEntity baseEntity) {

		return $insert(baseEntity);
	}

	@Transactional
	protected  BaseEntity delete(BaseEntity baseEntity) {

		$delete(baseEntity);
		
		return baseEntity;
	}

	protected  BaseEntity deleteById(BaseEntity baseEntity) {

		$deleteById(baseEntity);
		
		return baseEntity;
	}

	protected  void deleteAll(Iterable<? extends BaseEntity> entities) {

		$deleteAll(entities);
		
	}

	/**  PROTEGIDOS - SERVICE - VALIDATORS **/

	protected void validatorCommons(BaseEntity baseEntity) {
		
	}

	protected void validatorInsert(BaseEntity baseEntity) {
		
	}

	protected void validatorUpdate(BaseEntity baseEntity) {
		
	}

	protected void validatorDelete(BaseEntity baseEntity) {
		
	}

	/**  PROTEGIDOS - SERVICE - REPOSITORY **/

	protected BaseRepository getRepository() {

		ErrorDTO errorDTO = new ErrorDTO("service.error", "Metodo getRepository nao implementado na classe service");
		throw new BusinessException(errorDTO);
		
	}

	protected BaseConverter getConverter() {

		ErrorDTO errorDTO = new ErrorDTO("service.error", "Metodo getConverter nao implementado na classe service");
		throw new BusinessException(errorDTO);
		
	}

	/**  PRIVADOS - TRANSACIONALS **/
	
	@Transactional
	private BaseEntity $insert(BaseEntity baseEntity) {

		validatorCommons(baseEntity);
		
		validatorInsert(baseEntity);

		return $save(baseEntity);
	}
	
	@Transactional
	private BaseEntity $update(BaseEntity baseEntity) {

		validatorCommons(baseEntity);
		
		validatorUpdate(baseEntity);
		
		return $save(baseEntity);
		
	}

	@Transactional
	private BaseEntity $save(BaseEntity baseEntity) {

		try {
			return (BaseEntity) getRepository().save(baseEntity);
		}catch(BusinessException bex) {
			throw bex;
		}catch(DataIntegrityViolationException diEx) {
			ErrorDTO errorDTO = new ErrorDTO("database.fkintegrity.service.error", "entity.save.error", diEx);
			throw new BusinessException(errorDTO);
		}catch(JpaSystemException jpaEx) {
			throw jpaEx;
		} catch (Exception ex) {
			ErrorDTO errorDTO = new ErrorDTO("unexpected.service.error", "entity.save.error", ex);
			throw new BusinessException(errorDTO);
		}
	}

	@Transactional
	private void $delete(BaseEntity baseEntity) {

		validatorDelete(baseEntity);
		
		try {
			getRepository().delete(baseEntity);
			
		}catch(BusinessException bex) {
			throw bex;
		}catch(DataIntegrityViolationException diEx) {
			ErrorDTO errorDTO = new ErrorDTO("database.fkintegrity.service.error", "entity.delete.error", diEx);
			throw new BusinessException(errorDTO);
		}catch(JpaSystemException jpaEx) {
			throw jpaEx;
		} catch (Exception ex) {
			ErrorDTO errorDTO = new ErrorDTO("unexpected.service.error", "entity.delete.error", ex);
			throw new BusinessException(errorDTO);
		}
	}

	@Transactional
	private void $deleteById(BaseEntity baseEntity) {

		validatorDelete(baseEntity);

		try {

			Long id = (Long) baseEntity.getClass().getMethod("getId", null).invoke(baseEntity, null);
			getRepository().deleteById(id);

		}catch(BusinessException bex) {
			throw bex;
		}catch(DataIntegrityViolationException diEx) {
			ErrorDTO errorDTO = new ErrorDTO("database.fkintegrity.service.error", "entity.delete.error", diEx);
			throw new BusinessException(errorDTO);
		}catch(JpaSystemException jpaEx) {
			throw jpaEx;
		} catch (Exception ex) {
			ErrorDTO errorDTO = new ErrorDTO("unexpected.service.error", "entity.delete.error", ex);
			throw new BusinessException(errorDTO);
		}
				
	}

	@Transactional
	private void $deleteAll(Iterable<? extends BaseEntity> entities) {
		
		getRepository().deleteAll(entities);
	}

	
	private List<? extends BaseEntity> $listAll() {

		return (List<? extends BaseEntity>) getRepository().findAll();
	}

	private BaseEntity $findById(Long id){

		Optional<BaseEntity> baseEntityOpt = getRepository() .findById(id);
//		if(baseEntityOpt == null || baseEntityOpt.isEmpty()) {
//			throw new BusinessException(new ErrorDTO("service.error", "entity.not.found"));
//		}
		
		return baseEntityOpt.get();
	}


}

package br.com.segurosunimed.gestaocarteiras.commons.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public abstract interface BaseRepository<BaseEntity, Long> {

	<S extends BaseEntity> S save(S entity);
	
	List<BaseEntity> findAll();
	
	Optional<BaseEntity> findById(Long id);

	void delete(BaseEntity baseEntity);
	
	void deleteById(Long id);

	void deleteAll(Iterable<? extends BaseEntity> entities);	

}
			
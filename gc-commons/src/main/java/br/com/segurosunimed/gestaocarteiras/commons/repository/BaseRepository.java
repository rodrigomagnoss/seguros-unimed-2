package br.com.segurosunimed.gestaocarteiras.commons.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public abstract interface BaseRepository<T, E> {

	<S extends T> S save(S entity);
	
	List<T> findAll();
	
	Optional<T> findById(E id);

	void delete(T baseEntity);
	
	void deleteById(E id);

	void deleteAll(Iterable<? extends T> entities);	

}
			
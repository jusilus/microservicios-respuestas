package com.formacionbdi.microservicios.app.respuestas.models.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends MongoRepository<Respuesta, Long> {

	@Query("{'alumnoId': ?0, 'preguntaId': { $in: ?1}}")
	public Iterable<Respuesta> findRespuestaByAlumnoByPreguntaIds(Long alumnoId, Iterable<Long> preguntaIds);
}
package com.formacionbdi.microservicios.app.respuestas.service;

//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.formacionbdi.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.models.repository.RespuestaRepository;
//import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
//import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository respuestaRepository;

	//@Autowired
	//private ExamenFeignClient examenClient;

	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return respuestaRepository.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		/*
		// Obtenemos el examen
		Examen examen = examenClient.obtenerExamenPorId(examenId);
		// Obtenemos las preguntas del examen
		List<Pregunta> preguntas = examen.getPreguntas();
		// Obtenemos el id de cada pregunta del examen.
		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
		// Obtenemos un iterable con las respuestas (para un examen en particula) que ha dado un alumno en concreto.
		List<Respuesta> respuestas = (List<Respuesta>) respuestaRepository.findRespuestaByAlumnoByPreguntaIds(alumnoId,
				preguntaIds);
		// Asignamos cada objeto pregunta a cada objeto respuesta. Debe coincidir pregunta.getId con respuesta.preguntaId
		respuestas.stream().map(r -> {
			preguntas.forEach(p -> {
				if (p.getId() == r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList());
		*/
		List<Respuesta> respuestas = (List<Respuesta>) respuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return respuestas;
	}

	//Necesitamos conseguir los id de los examenes. Para ello necsitamos los id de las preguntas.
	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		/*
		//Con el alumno conseguimos las respuestas respondidas por éste.
		List<Respuesta> respuestasAlumno = (List<Respuesta>) respuestaRepository.findByAlumnoId(alumnoId);
		//Preparamos una Lista vacía.
		List<Long> examenIds = Collections.emptyList();
		if(respuestasAlumno.size() > 0) {
			//Con las respuestas del alumno conseguimos los id de las preguntas que ha respondido.
			List<Long> preguntaIds = respuestasAlumno.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());
			//Con los id de las preguntas respondidas obtenemos los id de los exámenes
			examenIds = examenClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
		}
		*/
		List<Respuesta> respuestasAlumno = (List<Respuesta>) respuestaRepository.findExamenesIdsConRespuestasByAlumno(alumnoId);
		List<Long> examenIds = respuestasAlumno
				.stream()
				.map(r -> r.getPregunta().getExamen().getId())
				.distinct()
				.collect(Collectors.toList());
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		return respuestaRepository.findByAlumnoId(alumnoId);
	}
}
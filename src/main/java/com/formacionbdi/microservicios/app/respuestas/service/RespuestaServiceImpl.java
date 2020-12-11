package com.formacionbdi.microservicios.app.respuestas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository respuestaRepository;

	@Autowired
	private ExamenFeignClient examenClient;

	@Override
	@Transactional
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return respuestaRepository.saveAll(respuestas);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
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
		return respuestas;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		return null;
	}
}
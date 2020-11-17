package com.formacionbdi.microservicios.app.respuestas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.service.RespuestaService;

@RestController
public class RespuestaController {

	@Autowired
	private RespuestaService respuestaService;

	@PostMapping
	public ResponseEntity<?> agregar(@RequestBody Iterable<Respuesta> respuestas) {
		Iterable<Respuesta> respuestasDb = respuestaService.saveAll(respuestas);
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
	}

	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?> obtenerRespuestasPorAlumnoPorExamen(@PathVariable Long alumnoId,
			@PathVariable Long examenId) {
		Iterable<?> respuestas = respuestaService.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return ResponseEntity.ok(respuestas);
	}
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondido")
	public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId){
		Iterable<Long> examenesId = respuestaService.findExamenesIdsConRespuestasByAlumno(alumnoId);
		return ResponseEntity.ok(examenesId);
	}	
}
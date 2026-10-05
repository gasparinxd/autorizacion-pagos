package com.pagos.autorizacion.pago;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos")
public class PagoController {

	private final PagoRepository repository;

	public PagoController(PagoRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	@Operation(summary = "Listar pagos")
	public List<Pago> listar() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener un pago")
	public ResponseEntity<Pago> obtener(@PathVariable Long id) {
		return ResponseEntity.of(repository.findById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Registrar un pago")
	public Pago crear(@RequestBody Pago pago) {
		pago.setId(null);
		if (pago.getEstado() == null) {
			pago.setEstado("PENDIENTE");
		}
		return repository.save(pago);
	}
}

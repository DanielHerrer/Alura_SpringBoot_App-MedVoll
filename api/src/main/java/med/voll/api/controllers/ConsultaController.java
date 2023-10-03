package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import med.voll.api.domain.consulta.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;
    @Autowired
    private ConsultaRepository repository;

    /**
     * POST
     * Requisitos:
     * Información requerida del médico:
     * - Long ID
     * - Paciente paciente;
     * - Medico medico;
     * - LocalDateTime fechaYhora;
     * Reglas del negocio:
     * - Las consultas tienen una duracion fija de 1 hora
     * - Las consultas deben agendarse con al menos 30 min. de anticipacion
     * - No permitir agendar citas con pacientes inactivos en el sistema
     * - No permitir agendar citas con medicos inactivos en el sistema
     * - No permitir agendar más de una consulta en el mismo dia para el mismo paciente
     * - No permitir agendar una cita con un medico que ya tiene otra cita programada en la misma fecha/hora
     * - La eleccion de un medico es opcional, en cuyo caso el sistema
     *      asignara uno aleatoriamente que este disponible en la fecha/hora ingresada
     */
    @PostMapping
    @Transactional
    @Operation(
            summary = "Registra una Consulta en la base de datos.",
            description = "",
            tags = { "consulta", "post" })
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta dto) {

        var response = service.agendar(dto);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE
     * - Cancelar Consulta
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Elimina lógicamente una Consulta por su ID.",
            description = "",
            tags = { "consulta", "delete" })
    public ResponseEntity cancelar(@PathVariable Long id, @RequestBody DatosCancelamientoConsulta dto) {
        Consulta consulta = repository.getReferenceById(id);
        service.cancelar(dto);

        return ResponseEntity.noContent().build();
    }

}

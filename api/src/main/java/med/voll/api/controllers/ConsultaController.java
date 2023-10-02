package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

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
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta dto) {

        service.agendar(dto);

        return ResponseEntity.ok(new DatosDetalleConsulta(null,null,null,null));
    }

}

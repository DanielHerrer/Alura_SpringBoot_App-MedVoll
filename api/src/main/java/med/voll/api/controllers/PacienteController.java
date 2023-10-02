package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    /**
     * @Autowired le estamos diciendo a Spring que el componente es un punto donde se debe inyectar una dependencia,
     *  en otras palabras, el componente se inyecta en la clase que lo posee,
     *   estableciendo una colaboración entre componentes.
     * No es recomendable por fines de testing. Si quieren hacer pruebas unitarias, van a tener problemas,
     *  va a ser muy difícil que puedan hacer un mock de MedicoRepository.
     */
    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * POST
     * - Long ID
     * - String nombre;
     * - String email;
     * - String telefono;
     * - String documento;
     * - [Class] Direccion direccion;
     */
    @PostMapping
    // Registra un paciente en la base de datos.
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody
                                                                    @Valid DatosRegistroPaciente dtoRegistroPaciente,
                                                                    UriComponentsBuilder uriComponentsBuilder) {
        // Guarda el paciente en la base de datos.
        Paciente paciente = pacienteRepository.save(new Paciente(dtoRegistroPaciente));
        // Prepara la respuesta con los datos del paciente registrado.
        DatosRespuestaPaciente dtoRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(), paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getEstado(), paciente.getDireccion().getPostal()));
        // Crea la URL para acceder a los detalles del paciente registrado.
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        // Retorna una respuesta exitosa (201 CREATED) con los datos del paciente y la URL.
        return ResponseEntity.created(url).body(dtoRespuestaPaciente);
    }

    /**
     * GET
     * Requisitos:
     * Información requerida del paciente:
     * - Nombre
     * - Documento
     * - Email
     * Reglas del negocio:
     * - Ordenado ascendentemente por nombre
     * - Paginado, maximo 10 registros por pagina
     */
    @GetMapping
    // Obtiene una lista paginada de pacientes.
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
        // Retorna una lista paginada de médicos ACTIVOS mapeados a objetos DatosListadoPaciente.
        return ResponseEntity.ok(pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new));
    }

    @GetMapping("/{id}")
    // Obtiene los detalles de un paciente por su ID.
    public ResponseEntity<DatosRespuestaPaciente> retornaDatosPaciente(@PathVariable Long id) {
        // Obtiene el paciente por su ID.
        Paciente paciente = pacienteRepository.getReferenceById(id);
        // Prepara la respuesta con los datos del paciente.
        DatosRespuestaPaciente dtoPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(), paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getEstado(), paciente.getDireccion().getPostal()));
        // Retorna una respuesta exitosa con los detalles del paciente.
        return ResponseEntity.ok(dtoPaciente);
    }

    /**
     * PUT
     * Información permitida para actualización:
     * - Nombre
     * - Telefono
     * - Dirección
     * Reglas del negocio:
     * - No permitir actualizar email, especialidad y documento
     */
    @PutMapping
    @Transactional
    // Actualiza los datos de un paciente por su ID.
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody
                                                                 @Valid DatosActualizarPaciente dtoActualizarPaciente) {
        // Obtiene el paciente por su ID.
        Paciente paciente = pacienteRepository.getReferenceById(dtoActualizarPaciente.id());
        // Actualiza los datos del paciente con la información proporcionada.
        paciente.actualizarDatos(dtoActualizarPaciente);
        // Retorna una respuesta exitosa con los datos actualizados del paciente.
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(), paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getEstado(), paciente.getDireccion().getPostal())));
    }

    /**
     * DELETE
     * Reglas del negocio:
     * - El registro no debe ser borrado de la base de datos
     */
    @DeleteMapping("/{id}")
    @Transactional
    // Elimina lógicamente un paciente por su ID.
    public ResponseEntity eliminarPaciente(@PathVariable Long id) {
        // Obtiene el paciente por su ID.
        Paciente paciente = pacienteRepository.getReferenceById(id);
        // Desactiva al paciente (eliminación lógica).
        paciente.desactivarPaciente();
        // Retorna una respuesta exitosa sin contenido (204).
        return ResponseEntity.noContent().build();
    }
}

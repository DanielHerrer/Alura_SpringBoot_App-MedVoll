package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
//@Api(value = "MedicoController", description = "Controlador para operaciones CRUD de médicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    /**
     * @Autowired le estamos diciendo a Spring que el componente es un punto donde se debe inyectar una dependencia,
     *  en otras palabras, el componente se inyecta en la clase que lo posee,
     *   estableciendo una colaboración entre componentes.
     * No es recomendable por fines de testing. Si quieren hacer pruebas unitarias, van a tener problemas,
     *  va a ser muy difícil que puedan hacer un mock de MedicoRepository.
     */
    @Autowired
    private MedicoRepository medicoRepository;

    /**
     * POST
     * - Long ID
     * - String nombre;
     * - String email;
     * - String telefono;
     * - String documento;
     * - [Enum] Especialidad especialidad;
     * - [Class] Direccion direccion;
     */
    @PostMapping
    @Operation(
            summary = "Registra un Medico en la base de datos.",
            description = "",
            tags = { "medico", "post" })
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody
                                                                @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        // Guarda el médico en la base de datos.
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        // Prepara la respuesta con los datos del médico registrado.
        DatosRespuestaMedico dtoRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getEstado(), medico.getDireccion().getPostal()));
        // Crea la URL para acceder a los detalles del médico registrado.
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        // Retorna una respuesta exitosa (201 CREATED) con los datos del médico y la URL.
        return ResponseEntity.created(url).body(dtoRespuestaMedico);
    }

    /**
     * GET
     * Requisitos:
     * Información requerida del médico:
     * - Nombre
     * - Especialidad
     * - Documento
     * - Email
     * Reglas del negocio:
     * - Ordenado ascendentemente por nombre
     * - Paginado, maximo 10 registros por pagina
     */
    @GetMapping
    @Operation(
            summary = "Obtiene una lista paginada de medicos activos.",
            description = "",
            tags = { "medico", "get" })
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 10, sort = "nombre")
                                                                       Pageable paginacion ) {
        // Retorna una lista paginada de médicos ACTIVOS mapeados a objetos DatosListadoMedico.
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtiene los detalles de un médico por su ID.",
            description = "",
            tags = { "medico", "get" })
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        // Obtiene el médico por su ID.
        Medico medico = medicoRepository.getReferenceById(id);
        // Prepara la respuesta con los datos del médico.
        DatosRespuestaMedico dtoMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getEstado(), medico.getDireccion().getPostal()));
        // Retorna una respuesta exitosa con los detalles del médico.
        return ResponseEntity.ok(dtoMedico);
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
    @Operation(
            summary = "Actualiza un médico.",
            description = "",
            tags = { "medico", "put" })
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico dto) {
        // Obtiene el médico por su ID.
        Medico medico = medicoRepository.getReferenceById(dto.id());
        // Actualiza los datos del médico con la información proporcionada.
        medico.actualizarDatos(dto);
        // Retorna una respuesta exitosa con los datos actualizados del médico.
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getEstado(), medico.getDireccion().getPostal())));
    }

    /**
     * DELETE
     * Reglas del negocio:
     * - El registro no debe ser borrado de la base de datos
     */

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Elimina lógicamente un médico por su ID.",
            description = "",
            tags = { "medico", "delete" })
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        // Obtiene el médico por su ID.
        Medico medico = medicoRepository.getReferenceById(id);
        // Desactiva al médico (eliminación lógica).
        medico.desactivarMedico();
        // Retorna una respuesta exitosa sin contenido (204).
        return ResponseEntity.noContent().build();
    }

    // DELETE EN BASE DE DATOS
/*
    public void eliminarMedico(@PathVariable Long id) { // PathVariable vincula el url de DeleteMapping
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }
*/
}
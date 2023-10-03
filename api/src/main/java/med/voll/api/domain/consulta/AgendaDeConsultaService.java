package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorHorarioAntecedencia;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired // Me trae todos los validadores que implementen la interfaz ValidadorDeConsultas
    private List<ValidadorDeConsultas> validadores;

    @Autowired
    private List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;

    public DatosDetalleConsulta agendar (DatosAgendarConsulta dto) {

        if (!pacienteRepository.findById(dto.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("El ID del Paciente no fue encontrado..");
        }

        if(dto.idMedico() != null && !medicoRepository.existsById(dto.idMedico())) {
            throw new ValidacionDeIntegridad("El ID del Medico no fue encontrado..");
        }

        validadores.forEach(v -> v.validar(dto)); // Recorre cada validador y valida el dto

        Paciente paciente = pacienteRepository.findById(dto.idPaciente()).get();

        Medico medico = seleccionarMedico(dto);

        if (medico == null) {
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario y especialidad.");
        }

        Consulta consulta = new Consulta(medico, paciente, dto.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    public void cancelar (DatosCancelamientoConsulta dto) {
        if(!consultaRepository.existsById(dto.idConsulta())) {
            throw new ValidacionDeIntegridad("El ID de la Consulta ingresada no existe..");
        }
        validadoresCancelamiento.forEach(v -> v.validar(dto));

        var consulta = consultaRepository.getReferenceById(dto.idConsulta());
        consulta.cancelar(dto.motivo());
    }

    private Medico seleccionarMedico (DatosAgendarConsulta dto) {
        if (dto.idMedico() != null) {
            return medicoRepository.getReferenceById(dto.idMedico());
        }
        if (dto.especialidad() == null) {
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el medico");
        }

        return medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(dto.especialidad(), dto.fecha());

    }

}

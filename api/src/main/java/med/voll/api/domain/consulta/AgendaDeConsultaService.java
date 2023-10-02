package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    public void agendar (DatosAgendarConsulta dto) {

        if (pacienteRepository.findById(dto.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("El ID del Paciente no fue encontrado..");
        }

        if(dto.idMedico() != null && medicoRepository.existsById(dto.idMedico())) {
            throw new ValidacionDeIntegridad("El ID del Medico no fue encontrado..");
        }

        Paciente paciente = pacienteRepository.findById(dto.idPaciente()).get();

        Medico medico = seleccionarMedico(dto);

        Consulta consulta = new Consulta(null, medico, paciente, dto.fecha());

        consultaRepository.save(consulta);

    }

    private Medico seleccionarMedico(DatosAgendarConsulta dto) {
        if (dto.idMedico() != null) {
            return medicoRepository.getReferenceById(dto.idMedico());
        }
        if (dto.especialidad() == null) {
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el medico");
        }

        return medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(dto.especialidad(), dto.fecha());

    }

}

package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta dto) {
        var primerHorario = dto.fecha().withHour(7);
        var ultimoHorario = dto.fecha().withHour(18);

        var pacienteConConsulta = repository.existsByPacienteIdAndFechaBetween(dto.idPaciente(),primerHorario,ultimoHorario);

        if (pacienteConConsulta) {
            throw new ValidationException("El Paciente ya tiene una consulta para ese día.");
        }

    }

}

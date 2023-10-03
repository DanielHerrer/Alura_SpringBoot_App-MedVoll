package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosCancelamientoConsulta (
        Long idConsulta,

        Long idPaciente,
        Long idMedico,
        MotivoCancelamiento motivo
){
}

package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(
        Long id,
        @NotNull
        Long idPaciente,
        Long idMedico,
        Especialidad especialidad,
        @NotNull
        @Future // Future para que la fecha indicada sea posterior a la actual
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fecha
) {
}

package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta dto) {
        // Lombok permite usar 'var'
        var ahora = LocalDateTime.now();
        var horaDeConsulta = dto.fecha();

        var diferenciaDe30Min = Duration.between(ahora,horaDeConsulta).toMinutes() < 30;

        if (diferenciaDe30Min) {
            throw new ValidationException("Las consultas deben programarse con al menos 30 minutos de anticipación.");
        }

    }
}

package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas {
    public void validar (DatosAgendarConsulta dto) {
        var domingo = DayOfWeek.SUNDAY.equals(dto.fecha().getDayOfWeek());
        var antesDeApertura = dto.fecha().getHour() < 7;
        var despuesDeCierre = dto.fecha().getHour() > 19;

        if (domingo || antesDeApertura || despuesDeCierre) {
            throw new ValidacionDeIntegridad("El horario de atención de la clinica es de" +
                    " Lunes a Sábado de 07:00 a 19:00 horas.");
        }
    }
}


package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta dto) {
        if (dto.idMedico() == null) {
            return;
        }
        var medicoConConsulta = repository.existsByMedicoIdAndFecha(dto.idMedico(),dto.fecha());

        if (medicoConConsulta) {
            throw new ValidationException("Este Medico ya tiene una consulta en ese horario.");
        }
    }

}

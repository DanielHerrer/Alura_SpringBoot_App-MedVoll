package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

// Esta interface nos brinda metodos DAO como insert/delete/update/read para PacienteController
@Repository
public interface ConsultaRepository extends JpaRepository <Consulta, Long> { // tipo de objeto y tipo de ID

    // Spring Data acepta la nomenclatura "findAll"
    //  y la mapea sola como una query SQL normal
    Page<Consulta> findAll(Pageable paginacion);

    Boolean  existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);
}

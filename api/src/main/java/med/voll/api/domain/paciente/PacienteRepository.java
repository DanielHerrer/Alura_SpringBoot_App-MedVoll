package med.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// Esta interface nos brinda metodos DAO como insert/delete/update/read para PacienteController
@Repository
public interface PacienteRepository extends JpaRepository <Paciente, Long> { // tipo de objeto y tipo de ID

    // Spring Data acepta la nomenclatura "findAll"
    //  y la mapea sola como una query SQL normal
    Page<Paciente> findAll(Pageable paginacion);

    @Query("""
            SELECT p.activo FROM Paciente p
            WHERE p.id = :idPaciente
            """)
    Boolean findActivoById(Long idPaciente);
}

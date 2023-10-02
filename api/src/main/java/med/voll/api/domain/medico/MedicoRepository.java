package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

// Esta interface nos brinda metodos DAO como insert/delete/update/read para MedicoController
@Repository
public interface MedicoRepository extends JpaRepository <Medico, Long> { // tipo de objeto y tipo de ID

    // Spring Data acepta la nomenclatura "findBy" + variable + condicion
    //  y la mapea sola como una query SQL normal
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            SELECT * FROM Medico m 
            WHERE m.activo = 1 AND 
            m.especialidad = :especialidad AND
            m.id NOT IN(
                SELECT c.medico.id FROM Consulta c
                WHERE c.fecha = :fecha
            )
            ORDER BY rand() LIMIT 1;
            """)
        // NOT INT() retorna medicos que NO se encuentren ocupados en esa fecha
        // rand() lo ordena aleatoriamente y me retorna el primero
    Medico seleccionarMedicoPorEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
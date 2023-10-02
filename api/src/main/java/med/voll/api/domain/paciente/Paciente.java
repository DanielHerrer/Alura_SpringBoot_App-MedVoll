package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.direccion.Direccion;

@Table(name="pacientes")
@Entity(name = "Paciente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String documento;

    @Embedded
    private Direccion direccion;

    private Boolean activo;

    public Paciente(DatosRegistroPaciente dto) {
        this.activo = true;
        this.nombre = dto.nombre();
        this.email = dto.email();
        this.documento = dto.documento();
        this.telefono = dto.telefono();
        this.direccion = new Direccion(dto.direccion());
    }

    public void actualizarDatos(DatosActualizarPaciente dto) {
        if (dto.nombre() != null) {
            this.nombre = dto.nombre();
        }
        if (dto.telefono() != null) {
            this.telefono = dto.telefono();
        }
        if (dto.direccion() != null) {
            this.direccion = direccion.actualizarDatos(dto.direccion());
        }
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}

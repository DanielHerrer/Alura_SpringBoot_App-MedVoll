package med.voll.api.domain.medico;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.direccion.Direccion;

@Table(name="medicos")
@Entity(name = "Medico")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String documento;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Embedded
    private Direccion direccion;

    private Boolean activo;

    public Medico(DatosRegistroMedico dto) {
        this.activo = true;
        this.nombre = dto.nombre();
        this.email = dto.email();
        this.documento = dto.documento();
        this.telefono = dto.telefono();
        this.especialidad = dto.especialidad();
        this.direccion = new Direccion(dto.direccion());
    }

    public void actualizarDatos(DatosActualizarMedico dto) {
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

    public void desactivarMedico() {
        this.activo = false;
    }
}
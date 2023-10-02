package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Direccion {

    private String calle;
    private String numero;
    private String complemento;
    private String ciudad;
    private String estado;
    private String postal;

    public Direccion(DatosDireccion dto) {
        this.calle = dto.calle();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.ciudad = dto.ciudad();
        this.estado = dto.estado();
        this.postal = dto.postal();
    }

    public Direccion actualizarDatos(DatosDireccion dto) {
        this.calle = dto.calle();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.ciudad = dto.ciudad();
        this.estado = dto.estado();
        this.postal = dto.postal();
        return this;
    }
}

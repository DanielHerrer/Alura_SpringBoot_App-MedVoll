// D.T.O.

package med.voll.api.domain.direccion;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

// Record es un recurso que le permite representar una clase inmutable, que contiene solo atributos,
//  constructor y métodos de lectura, de una manera muy simple y ágil.
//    La anotación @JsonAlias sirve para mapear "alias" alternativos para los campos que
//     se recibirán del JSON, y es posible asignar múltiples alias
public record DatosDireccion(
        @NotBlank String calle,
        String numero,
        String complemento,
        @NotBlank String ciudad,
        @JsonAlias({"distrito", "estado"})
        @NotBlank String estado,
        @JsonAlias({"codigoPostal", "codigo_postal", "postal"})
        @NotBlank String postal
) { }

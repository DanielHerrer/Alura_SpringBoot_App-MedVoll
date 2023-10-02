package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

// Record es un recurso que le permite representar una clase inmutable, que contiene solo atributos, constructor y
// métodos de lectura, de una manera muy simple y ágil.
public record DatosRegistroMedico(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,
        @NotBlank(message = "Email es obligatorio")
        @Email(message = "Formato de email es inválido")
        String email,
        @NotBlank(message = "Teléfono es obligatorio")
        @Pattern(regexp = "\\d{6,20}", message = "Formato del Telefono es inválido")
        String telefono,
        @NotBlank(message = "Documento es obligatorio")
        @Pattern(regexp = "\\d{4,6}", message = "Formato del Documento es inválido")
        String documento,
        @NotNull(message = "Especialidad es obligatorio")
        Especialidad especialidad,
        @NotNull(message = "Datos de dirección son obligatorios")
        @Valid
        DatosDireccion direccion
) { }

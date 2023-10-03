package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}") // Obtiene el secreto de seguridad desde la configuración
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // Crea un algoritmo para firmar el JWT
            return JWT.create()
                    .withIssuer("voll med") // Define el emisor del JWT
                    .withSubject(usuario.getUsuario()) // Define el destinatario del JWT
                    .withClaim("id", usuario.getId()) // Agrega un claim personalizado (ID)
                    .withExpiresAt(generarFechaExpiracion()) // Define la fecha de expiración
                    .sign(algorithm); // Firma el JWT

        } catch (JWTCreationException exception) {
            throw new RuntimeException(); // Maneja excepciones al crear el JWT
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException(); // Lanza una excepción si el token es nulo
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // Validando firma del token
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build()
                    .verify(token); // Verifica la firma del JWT
            verifier.getSubject(); // Obtiene el sujeto del JWT

        } catch (JWTVerificationException exception){
            System.out.println(exception.toString()); // Maneja excepciones al verificar el JWT
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier invalido"); // Lanza una excepción si el verificador es inválido
        }
        return verifier.getSubject(); // Devuelve el sujeto del JWT verificado
    }

    private Instant generarFechaExpiracion() { // Genera la fecha de expiración (2 horas de sesion)
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}

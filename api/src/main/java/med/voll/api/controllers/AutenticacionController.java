package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJWTToken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    // Este método maneja las solicitudes POST a la ruta "/login".
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario dto) {
        // Crear un objeto de autenticación con las credenciales proporcionadas.
        Authentication authToken = new UsernamePasswordAuthenticationToken(dto.usuario(), dto.clave());
        // Autenticar al usuario utilizando el AuthenticationManager configurado.
        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);
        // Generar un token JWT basado en el usuario autenticado.
        String JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        // Devolver una respuesta exitosa con el token JWT generado.
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

}

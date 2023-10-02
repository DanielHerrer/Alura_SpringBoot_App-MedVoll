package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtener el token del header Authorization, luego 'limpia' el String quitando la etiqueta bearer
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String token = authHeader.replace("Bearer ","");
            String nombreUsuario = tokenService.getSubject(token); // extract username
            if (nombreUsuario != null) {
                // Token valido
                UserDetails usuario = usuarioRepository.findByUsuario(nombreUsuario);
                Authentication authentication = new UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities()); // Forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}

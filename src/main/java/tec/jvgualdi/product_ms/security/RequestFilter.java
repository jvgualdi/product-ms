package tec.jvgualdi.product_ms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tec.jvgualdi.product_ms.exceptions.InvalidJwtException;
import java.io.IOException;
import java.util.List;

public class RequestFilter extends OncePerRequestFilter {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Algorithm alg = Algorithm.HMAC256(jwtSecret);
                DecodedJWT jwt = JWT.require(alg)
                        .withIssuer("e‑commerce")
                        .build()
                        .verify(token);

                String email = jwt.getSubject();
                String role  = jwt.getClaim("role").asString();

                var user = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );
                SecurityContextHolder.getContext().setAuthentication(user);

            } catch (JWTVerificationException e) {
                throw new InvalidJwtException("Invalid or expired JWT token", e);
            }
        }
        filterChain.doFilter(request, response);
    }


}

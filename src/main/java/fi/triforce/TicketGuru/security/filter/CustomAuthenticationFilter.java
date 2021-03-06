package fi.triforce.TicketGuru.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        private final AuthenticationManager authenticationManager;
        private String tokenSecret;

        public CustomAuthenticationFilter(AuthenticationManager authenticationManager, String secret) {
                this.authenticationManager = authenticationManager;
                this.tokenSecret = secret;
        }




        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                        throws AuthenticationException {
                try {
                        ObjectMapper mapper = new ObjectMapper();
                        LoginDao login = mapper.readValue(request.getInputStream(), LoginDao.class);
                        String username = login.getUsername();
                        String password = login.getPassword();
                        log.info("Username is: {}", username);
                        log.info("Password is: {}", password);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                        username,
                                        password);
                        return authenticationManager.authenticate(authenticationToken);
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }

        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                        FilterChain chain,
                        Authentication authentication) throws IOException, ServletException {
                User user = (User) authentication.getPrincipal();
                Algorithm algorithm = Algorithm.HMAC256(tokenSecret.getBytes());
                String access_token = JWT.create()
                                .withSubject(user.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                                .withIssuer(request.getRequestURL().toString())
                                .withClaim("roles",
                                                user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList()))
                                .sign(algorithm);
                String refresh_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);
                //Roolin vienti tokenin mukana
                String role = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()).get(0);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                tokens.put("role", role);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        }

}

@Data
class LoginDao {
        String username;
        String password;
}

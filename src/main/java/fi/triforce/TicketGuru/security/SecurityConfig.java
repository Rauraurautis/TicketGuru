package fi.triforce.TicketGuru.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fi.triforce.TicketGuru.security.filter.CustomAuthenticationFilter;
import fi.triforce.TicketGuru.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${token.secret}")
        private String tokenSecret;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            System.out.println(tokenSecret + " over here");
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/api/token/refresh/**").permitAll();
        // Admin only
        http.authorizeRequests().antMatchers("/api/users/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/role/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/events/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/events/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/events/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/venues/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/venues/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/venues/**").hasAnyAuthority("ROLE_ADMIN");

        // & Ticket inspector

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/events/*/tickets/**").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_TICKETINSPECTOR", "ROLE_SALES");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/events/singleticket/**").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_TICKETINSPECTOR", "ROLE_SALES");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/events/tickets").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_SALES", "ROLE_TICKETINSPECTOR");

        // & Ticket sales
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/events/**").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_SALES");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/sales/**").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_SALES");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/salesevents/**").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_SALES");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/venues/**").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_SALES");
        
        http.cors();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), tokenSecret));
        http.addFilterBefore(new CustomAuthorizationFilter(tokenSecret), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

}

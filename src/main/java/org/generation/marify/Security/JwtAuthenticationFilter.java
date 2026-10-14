package org.generation.marify.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.generation.marify.services.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        System.out.println("1. HEADER RECIBIDO: " + authHeader);
        //verificamos si no hay token , si no hay no hace nada y spring security bloquea la peticion
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("-> REBOTADO: No hay header o no empieza con Bearer ");
            filterChain.doFilter(request, response);
            return;
        }
        //extraer token y email
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractEmail(jwt);
        System.out.println("2. EMAIL EXTRAÍDO DEL TOKEN: " + userEmail);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isValidToken(jwt, userDetails.getUsername())){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null, Collections.emptyList()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Registramos el usuario como autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("3. ¡AUTENTICACIÓN EXITOSA EN SECURITY CONTEXT!");
            }
            System.out.println("-> REBOTADO: El token no pasó isTokenValid()");
        }
        filterChain.doFilter(request,response);

    }
}

package com.santyman.hospital.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getTokenFromRequest(request);
		
		if (token == null) {
        filterChain.doFilter(request, response);
        return;
    }
		try {
			String username = jwtUtil.getUsernameFromToken(token);
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

			if (!jwtUtil.validateToken(token, userDetails)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido");
				return;
			}

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}

			filterChain.doFilter(request, response);
		} catch (UsernameNotFoundException exception) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
		} catch (Exception exception) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No se pudo autenticar el token");
		}
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return null;
		}

		return authorizationHeader.substring(7);
	}
}

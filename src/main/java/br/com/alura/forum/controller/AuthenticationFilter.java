	package br.com.alura.forum.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

public class AuthenticationFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;
	
	public AuthenticationFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String token = obterToken(request);
		boolean isValido = tokenService.isValido(token);
		if (isValido) {
			autenticar(token);
		}
		
		chain.doFilter(request, response);
	}

	private String obterToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (Strings.isNullOrEmpty(token) || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.replace("Bearer ", "");
	}
	
	private void autenticar(String token) {
		Long usuarioId = tokenService.getUsuarioId(token);
		Usuario usuario = usuarioRepository.findById(usuarioId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}

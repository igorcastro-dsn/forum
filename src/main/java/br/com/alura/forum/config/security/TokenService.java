package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataDeExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				   .setIssuer("API do fórum da Alura")
				   .setSubject(String.valueOf(usuarioLogado.getId()))
				   .setIssuedAt(hoje)
				   .setExpiration(dataDeExpiracao)
				   .signWith(SignatureAlgorithm.HS256, secret)
				   .compact();
	}

	public boolean isValido(String token) {
		try {
			// If the compact string presented does not reflect a Claims JWS, an UnsupportedJwtException will bethrown.
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token); 
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Long getUsuarioId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.valueOf(claims.getSubject());
	}

}

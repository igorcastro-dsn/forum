package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.modelo.Resposta;

public class RespostaDto {

	private Long id;
	private String mensagem;
	private LocalDateTime dataDeCriacao;
	private String nomeDoAutor;

	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataDeCriacao = resposta.getDataCriacao();
		this.nomeDoAutor = resposta.getAutor().getNome();
	}

	public Long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataDeCriacao() {
		return dataDeCriacao;
	}

	public String getNomeDoAutor() {
		return nomeDoAutor;
	}

}

package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	/**
	 * O spring utiliza uma convenção de nome para gerar automaticamente a query.
	 * Ou seja, para esse caso ele irá buscar o atributo "nome" dentro do 
	 * relacionamento "Curso". É possível também utilizar um "_" para separar o 
	 * atributo da classe (caso esteja gerando conflito com outros atributos)
	 * 
	 * @param nomeDoCurso
	 * @return lista de Topico
	 */
	List<Topico> findByCursoNome(String nomeDoCurso);
	
	
	/**
	 * Caso não queira seguir o padrão de nomes do spring é possível botar outro
	 * nome qualquer, porém para esse caso é necessário "ensinar" a query pro spring data.
	 * @param nomeDoCurso
	 * @return lista de Topico
	 */
	//@Query("<query aqui>")
	//List<Topico> listarTopicosDoCurso(@Param(value = "nomeDoCurso") String nomeDoCurso);
	
}

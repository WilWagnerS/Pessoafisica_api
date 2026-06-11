package br.com.senac.Pessoafisica_api.repositorios;

import br.com.senac.Pessoafisica_api.entidades.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepositorio extends JpaRepository<PessoaFisica, Long> {
}
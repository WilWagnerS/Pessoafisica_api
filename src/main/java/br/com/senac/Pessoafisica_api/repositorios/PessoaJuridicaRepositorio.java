package br.com.senac.Pessoafisica_api.repositorios;

import br.com.senac.Pessoafisica_api.entidades.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaJuridicaRepositorio extends JpaRepository<PessoaJuridica, Long> {
    List<PessoaJuridica> findByEmail(String email);
}
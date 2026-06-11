package br.com.senac.Pessoafisica_api.services;

import br.com.senac.Pessoafisica_api.dtos.PessoaFisicaDto;
import br.com.senac.Pessoafisica_api.entidades.PessoaFisica;
import br.com.senac.Pessoafisica_api.repositorios.PessoaFisicaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaFisicaService {

    private PessoaFisicaRepositorio pessoaFisicaRepositorio;

    public PessoaFisicaService(PessoaFisicaRepositorio pessoaFisicaRepositorio) {
        this.pessoaFisicaRepositorio = pessoaFisicaRepositorio;
    }

    public PessoaFisica criar(PessoaFisicaDto pessoa) {

        // implemtando a regra de negocio
        this.validarQuantidadeCpf(pessoa.getCpf());

        PessoaFisica pessoaFisicaPersist = this.pessoaFisicaDtoParaPessoaFisica(pessoa);

        return pessoaFisicaRepositorio.save(pessoaFisicaPersist);

    }

    public PessoaFisica atualizar(Long id, PessoaFisicaDto pessoa) {

        // reutlizar a regra de negocio
        this.validarQuantidadeCpf(pessoa.getCpf());

        if(pessoaFisicaRepositorio.existsById(id)) {
            PessoaFisica pessoaFisicaPersist = this.pessoaFisicaDtoParaPessoaFisica(pessoa);
            pessoaFisicaPersist.setId(id);

            return pessoaFisicaRepositorio.save(pessoaFisicaPersist);
        }

        throw new RuntimeException("Pessoa não encontrada!");
    }

    public void deletar(Long id) {
        if(pessoaFisicaRepositorio.existsById(id)) {
            pessoaFisicaRepositorio.deleteById(id);

            // Faltou o returno :D
            return;
        }

        throw new RuntimeException("Pessoa não encontrada!");
    }

    public List<PessoaFisica> listar(){
        return pessoaFisicaRepositorio.findAll();
    }

    private PessoaFisica pessoaFisicaDtoParaPessoaFisica(PessoaFisicaDto entrada) {
        PessoaFisica saida = new PessoaFisica();
        saida.setCpf(entrada.getCpf());
        saida.setEmail(entrada.getEmail());
        saida.setNome(entrada.getNome());
        saida.setDataNascimento(entrada.getDataNascimento());

        return saida;
    }

    private void validarQuantidadeCpf(String cpf) {
        if(cpf == null) {
            throw new RuntimeException("CPF deve ser informado!");
        }

        if(cpf.length() != 11) {
            throw new RuntimeException("Cpf invalido!");
        }
    }
}
package br.com.senac.Pessoafisica_api.services;

import br.com.senac.Pessoafisica_api.dtos.PessoaJuridicaDto;
import br.com.senac.Pessoafisica_api.entidades.PessoaJuridica;
import br.com.senac.Pessoafisica_api.repositorios.PessoaJuridicaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaJuridicaService {
    private PessoaJuridicaRepositorio pessoaJuridicaRepositorio;

    public PessoaJuridicaService(PessoaJuridicaRepositorio pessoaJuridicaRepositorio) {
        this.pessoaJuridicaRepositorio = pessoaJuridicaRepositorio;
    }

    public PessoaJuridica criar(PessoaJuridicaDto pessoa) {

        // implemtando a regra de negocio
        this.validarQuantidadeCnpj(pessoa.getCnpj());

        // regra de negocio do email
        this.validarEmail(pessoa.getEmail());

        PessoaJuridica pessoaJurificaPersist = this.pessoaJuridicaDtoParaPessoaFisica(pessoa);

        return pessoaJuridicaRepositorio.save(pessoaJurificaPersist);

    }

    public PessoaJuridica atualizar(Long id, PessoaJuridicaDto pessoa) {

        // reutlizar a regra de negocio
        this.validarQuantidadeCnpj(pessoa.getCnpj());

        // busca registro atual por ID
        Optional<PessoaJuridica> pessoaResult = pessoaJuridicaRepositorio.findById(id);

        // Se existir na base atualiza
        if(pessoaResult.isPresent()) {
            PessoaJuridica pessoaJurificaPersist = this.pessoaJuridicaDtoParaPessoaFisica(pessoa);
            pessoaJurificaPersist.setId(id);

            // Mantem o email atual
            pessoaJurificaPersist.setEmail(pessoaResult.get().getEmail());

            return pessoaJuridicaRepositorio.save(pessoaJurificaPersist);
        }

        throw new RuntimeException("Pessoa não encontrada!");
    }

    public void deletar(Long id) {
        if(pessoaJuridicaRepositorio.existsById(id)) {
            pessoaJuridicaRepositorio.deleteById(id);
            return;
        }

        throw new RuntimeException("Pessoa não encontrada!");
    }

    public List<PessoaJuridica> listar(){
        return pessoaJuridicaRepositorio.findAll();
    }

    private PessoaJuridica pessoaJuridicaDtoParaPessoaFisica(PessoaJuridicaDto entrada) {
        PessoaJuridica saida = new PessoaJuridica();
        saida.setCnpj(entrada.getCnpj());
        saida.setEmail(entrada.getEmail());
        saida.setDataFundacao(entrada.getDataFundacao());
        saida.setRazaoSocial(entrada.getRazaoSocial());
        saida.setInscricaoEstadual(entrada.getInscricaoEstadual());

        return saida;
    }

    private void validarQuantidadeCnpj(String cpf) {
        if(cpf == null) {
            throw new RuntimeException("CNPJ deve ser informado!");
        }

        if(cpf.length() != 14) {
            throw new RuntimeException("CNPJ invalido!");
        }
    }

    private void validarEmail(String email) {
        if( email != null) {
            List<PessoaJuridica> emailsList = pessoaJuridicaRepositorio.findByEmail(email);
            if(!emailsList.isEmpty()) {
                throw new RuntimeException("Email já em uso por outro cliente!");
            }
        } else {
            throw new RuntimeException("Email deve ser informado");
        }
    }
}

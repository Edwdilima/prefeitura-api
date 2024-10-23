package com.br.prefeitura.services;

import com.br.prefeitura.dtos.LicitacaoDTO;
import com.br.prefeitura.dtos.LicitacaoStatusDTO;
import com.br.prefeitura.dtos.ObraDTO;
import com.br.prefeitura.dtos.ObraStatusDTO;
import com.br.prefeitura.exceptions.SecretariaNaoEncontradaException;
import com.br.prefeitura.exceptions.ViolacaoRegraNegocioException;
import com.br.prefeitura.entities.*;
import com.br.prefeitura.repositories.LicitacaoRepository;
import com.br.prefeitura.repositories.ObraRepository;
import com.br.prefeitura.repositories.SecretariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecretariaService {

    private final ObraService obraService;

    public SecretariaService(@Lazy ObraService obraService) {
        this.obraService = obraService;
    }

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LicitacaoService licitacaoService;

    @Autowired
    private LicitacaoRepository licitacaoRepository;

    @Autowired
    private ObraRepository obraRepository;




    public Secretaria save(Secretaria secretaria){
        return secretariaRepository.save(secretaria);
    }


    public List<Secretaria> findAll() {
        return secretariaRepository.findAll();
    }


    public Optional<Secretaria> findById(Long secretariaId) {
        Optional<Secretaria> secretariaOpt = secretariaRepository.findById(secretariaId);
        if (secretariaOpt.isPresent()) {
            return secretariaOpt;
        } else {
            throw new SecretariaNaoEncontradaException(secretariaId);
        }
    }



    public Licitacao abrirLicitacao(Long usuario_id, Long secretaria_id, LicitacaoDTO licitacaoDTO){

        Usuario usuario = usuarioService.findById(usuario_id).get();
        Secretaria secretaria = findById(secretaria_id).get();
        Prefeitura prefeitura = secretaria.getPrefeitura();

        if(usuario.isAdmin()){
            Licitacao licitacao = new Licitacao();
            licitacao.setPrefeitura(prefeitura);
            licitacao.setSecretaria(secretaria);
            licitacao.setNumero(licitacaoDTO.getNumero());
            licitacao.setValorEstimado(licitacaoDTO.getValorEstimado());
            licitacao.setTipo(licitacaoDTO.getTipo());
            licitacao.setStatusLicitacao(licitacaoDTO.getStatusLicitacao());
            return licitacaoRepository.save(licitacao);
        }else{
            throw new ViolacaoRegraNegocioException("Esse usuário não pode criar uma licitação!");
        }
    }


    public Licitacao alterarStatusLicitacao(Long usuario_id, Long licitacao_id, LicitacaoStatusDTO licitacaoStatusDTO){

        Usuario usuario = usuarioService.findById(usuario_id).get();
        Licitacao licitacao = licitacaoService.findById(licitacao_id).get();

        if(usuario.isAdmin()){
            licitacao.setStatusLicitacao(licitacaoStatusDTO.getStatusLicitacao());
            return licitacaoRepository.save(licitacao);
        }else{
            throw new ViolacaoRegraNegocioException("Esse usuário não pode alterar uma licitação!");
        }
    }


    public Licitacao findLicitacaoById(Long secretaria_id, Long licitacao_id){
        Secretaria secretaria = findById(secretaria_id).get();
        return secretaria.findLicitacaoById(licitacao_id);
    }


    public List<Licitacao> findAllLicitacoes(Long secretaria_id){
        Secretaria secretaria = findById(secretaria_id).get();
        return secretaria.getLicitacoes();
    }


    public Obra iniciarObra(Long usuario_id, Long licitacao_id, Long secretaria_id, ObraDTO obraDTO){

        Usuario usuario = usuarioService.findById(usuario_id).get();
        Licitacao licitacao = licitacaoService.findById(licitacao_id).get();
        Secretaria secretaria = findById(secretaria_id).get();
        Prefeitura prefeitura = secretaria.getPrefeitura();

        if(usuario.isAdmin()){
            Obra obra = new Obra();
            obra.setLicitacao(licitacao);
            obra.setSecretaria(secretaria);
            obra.setDescricao(obraDTO.getDescricao());
            return obraRepository.save(obra);
        }else{
            throw new ViolacaoRegraNegocioException("Esse usuário não pode criar uma obra!");
        }
    }


    public List<Obra> findAllObras(Long secretaria_id){
        Secretaria secretaria = findById(secretaria_id).get();
        return secretaria.getObras();
    }


    public Obra findObraById(Long secretaria_id, Long obra_id){
        Secretaria secretaria = findById(secretaria_id).get();
        return secretaria.findObraById(obra_id);
    }



    public Obra alterarStatusObra(Long usuario_id, Long obra_id, ObraStatusDTO obraStatusDTO){

        Usuario usuario = usuarioService.findById(usuario_id).get();
        Obra obra = obraService.findById(obra_id).get();

        if(usuario.isAdmin()){
            obra.setStatusObra(obraStatusDTO.getStatusObra());
            return obraRepository.save(obra);
        }else{
            throw new ViolacaoRegraNegocioException("Esse usuário não pode criar uma licitação!");
        }
    }


}

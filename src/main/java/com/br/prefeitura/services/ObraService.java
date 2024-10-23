package com.br.prefeitura.services;
import com.br.prefeitura.dtos.ObraDTO;
import com.br.prefeitura.exceptions.ObraNaoEncontradaException;
import com.br.prefeitura.exceptions.ViolacaoRegraNegocioException;
import com.br.prefeitura.entities.*;
import com.br.prefeitura.repositories.ContratoRepository;
import com.br.prefeitura.repositories.NotaRepository;
import com.br.prefeitura.repositories.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObraService {

    private final SecretariaService secretariaService;

    // Injeção com @Lazy para evitar a referência circular imediata
    public ObraService(@Lazy SecretariaService secretariaService) {
        this.secretariaService = secretariaService;
    }

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private LicitacaoService licitacaoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ContratoService contratoService;


    public Optional<Obra> findById(Long id) {

        Optional<Obra> obraOptional = obraRepository.findById(id);
        if(obraOptional.isPresent()){
            return obraOptional;
        }else{
            throw new ObraNaoEncontradaException(id);
        }
    }

    public Obra salvar(Long licitacao_id, Long usuario_id, Long secretaria_id, ObraDTO obraDTO){
        Licitacao licitacao = licitacaoService.findById(licitacao_id).get();
        Usuario usuario = usuarioService.findById(usuario_id).get();
        Secretaria secretaria = secretariaService.findById(secretaria_id).get();

        if(usuario.isAdmin()){
            Obra novaObra = new Obra();
            novaObra.setDescricao(obraDTO.getDescricao());
            novaObra.setSecretaria(secretaria);
            novaObra.setLicitacao(licitacao);
            return obraRepository.save(novaObra);
        }else{
            throw new ViolacaoRegraNegocioException("Apenas usuarios ADMIN's podem criar uma obra");
        }
    }

    public Contrato gerarContrato(Long usuario_id, Long obra_id, Contrato contrato){
        Usuario usuario = usuarioService.findById(usuario_id).get();
        Obra obra = findById(obra_id).get();

        if(usuario.isAdmin()){
            contrato.setObra(obra);
            return contratoRepository.save(contrato);
        }else{
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem gerar um contrato");
        }
    }

    public Nota gerarNota(Long usuario_id, Long obra_id, Nota nota){
        Usuario usuario = usuarioService.findById(usuario_id).get();
        Obra obra = findById(obra_id).get();

        if(usuario.isAdmin()){
            nota.setObra(obra);
            return notaRepository.save(nota);
        }else{
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem gerar uma nota");
        }
    }



    public Contrato getContrato(Long usuario_id, Long obra_id){
        Usuario usuario = usuarioService.findById(usuario_id).get();
        Obra obra = findById(obra_id).get();

        if(usuario.isAdmin()){
            return obra.getContrato();
        }else{
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem ver os contratos");
        }
    }


    public List<Nota> getNotas(Long usuario_id, Long obra_id){
        Usuario usuario = usuarioService.findById(usuario_id).get();
        Obra obra = findById(obra_id).get();

        if(usuario.isAdmin()){
            return obra.getNotas();
        }else{
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem ver os contratos");
        }
    }

}

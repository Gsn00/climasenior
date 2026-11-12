package br.com.fiap.climasenior.service;

import br.com.fiap.climasenior.dto.CidadaoDTO;
import br.com.fiap.climasenior.exception.ResourceNotFoundException;
import br.com.fiap.climasenior.model.Cidadao;
import br.com.fiap.climasenior.repository.CidadaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadaoService {

    private final CidadaoRepository cidadaoRepository;

    public CidadaoService(CidadaoRepository cidadaoRepository) {
        this.cidadaoRepository = cidadaoRepository;
    }

    public List<CidadaoDTO> listarTodos() {
        return cidadaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CidadaoDTO> buscarPorBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            return listarTodos();
        }
        return cidadaoRepository.findByBairroIgnoreCaseContainingOrderByNomeCompletoAsc(bairro)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CidadaoDTO> listarComMobilidadeReduzida() {
        return cidadaoRepository.findByMobilidadeReduzidaTrueOrderByNomeCompletoAsc()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CidadaoDTO buscarPorId(Long id) {
        Cidadao cidadao = buscarEntidadePorId(id);
        return toDTO(cidadao);
    }

    public Cidadao buscarEntidadePorId(Long id) {
        return cidadaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidadão", id));
    }

    public CidadaoDTO salvar(CidadaoDTO dto) {
        if (cidadaoRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("Já existe um cidadão cadastrado com o CPF: " + dto.getCpf());
        }

        Cidadao cidadao = new Cidadao();
        cidadao.setNomeCompleto(dto.getNomeCompleto());
        cidadao.setCpf(dto.getCpf());
        cidadao.setDataNascimento(dto.getDataNascimento());
        cidadao.setTelefone(dto.getTelefone());
        cidadao.setBairro(dto.getBairro());
        cidadao.setObservacaoSaude(dto.getObservacaoSaude());
        cidadao.setMobilidadeReduzida(dto.getMobilidadeReduzida() != null ? dto.getMobilidadeReduzida() : false);

        Cidadao salvo = cidadaoRepository.save(cidadao);
        return toDTO(salvo);
    }

    public CidadaoDTO atualizar(Long id, CidadaoDTO dto) {
        Cidadao cidadao = buscarEntidadePorId(id);

        cidadaoRepository.findByCpf(dto.getCpf()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new IllegalArgumentException("Já existe outro cidadão cadastrado com o CPF: " + dto.getCpf());
            }
        });

        cidadao.setNomeCompleto(dto.getNomeCompleto());
        cidadao.setCpf(dto.getCpf());
        cidadao.setDataNascimento(dto.getDataNascimento());
        cidadao.setTelefone(dto.getTelefone());
        cidadao.setBairro(dto.getBairro());
        cidadao.setObservacaoSaude(dto.getObservacaoSaude());
        cidadao.setMobilidadeReduzida(dto.getMobilidadeReduzida() != null ? dto.getMobilidadeReduzida() : false);

        Cidadao atualizado = cidadaoRepository.save(cidadao);
        return toDTO(atualizado);
    }

    public void excluir(Long id) {
        Cidadao cidadao = buscarEntidadePorId(id);
        cidadaoRepository.delete(cidadao);
    }

    public long contarTotal() {
        return cidadaoRepository.count();
    }

    public long contarMobilidadeReduzida() {
        return cidadaoRepository.countByMobilidadeReduzidaTrue();
    }

    public CidadaoDTO toDTO(Cidadao entity) {
        return new CidadaoDTO(
                entity.getId(),
                entity.getNomeCompleto(),
                entity.getCpf(),
                entity.getDataNascimento(),
                entity.getTelefone(),
                entity.getBairro(),
                entity.getObservacaoSaude(),
                entity.getMobilidadeReduzida()
        );
    }
}
package br.com.fiap.climasenior.controller;

import br.com.fiap.climasenior.dto.CidadaoDTO;
import br.com.fiap.climasenior.service.CidadaoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cidadaos")
public class CidadaoController {

    private final CidadaoService cidadaoService;

    public CidadaoController(CidadaoService cidadaoService) {
        this.cidadaoService = cidadaoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String bairro,
                         @RequestParam(required = false) Boolean mobilidadeReduzida,
                         Model model) {
        if (Boolean.TRUE.equals(mobilidadeReduzida)) {
            model.addAttribute("cidadaos", cidadaoService.listarComMobilidadeReduzida());
            model.addAttribute("filtroMobilidade", true);
        } else if (bairro != null && !bairro.isBlank()) {
            model.addAttribute("cidadaos", cidadaoService.buscarPorBairro(bairro));
            model.addAttribute("bairroFiltro", bairro);
        } else {
            model.addAttribute("cidadaos", cidadaoService.listarTodos());
        }
        return "cidadaos/lista";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("cidadao", new CidadaoDTO());
        return "cidadaos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("cidadao") CidadaoDTO dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cidadaos/form";
        }

        try {
            if (dto.getId() == null) {
                cidadaoService.salvar(dto);
                redirectAttributes.addFlashAttribute("mensagemSucesso", "Cidadão cadastrado com sucesso!");
            } else {
                cidadaoService.atualizar(dto.getId(), dto);
                redirectAttributes.addFlashAttribute("mensagemSucesso", "Dados do cidadão atualizados com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("erroNegocio", e.getMessage());
            return "cidadaos/form";
        }

        return "redirect:/cidadaos";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        CidadaoDTO dto = cidadaoService.buscarPorId(id);
        model.addAttribute("cidadao", dto);
        return "cidadaos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        cidadaoService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cadastro do cidadão removido com sucesso!");
        return "redirect:/cidadaos";
    }
}
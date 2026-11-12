package br.com.fiap.climasenior.controller;

import br.com.fiap.climasenior.dto.PontoApoioDTO;
import br.com.fiap.climasenior.model.enums.TipoPontoApoio;
import br.com.fiap.climasenior.service.PontoApoioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pontos")
public class PontoApoioController {

    private final PontoApoioService pontoApoioService;

    public PontoApoioController(PontoApoioService pontoApoioService) {
        this.pontoApoioService = pontoApoioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String bairro, Model model) {
        if (bairro != null && !bairro.isBlank()) {
            model.addAttribute("pontos", pontoApoioService.buscarPorBairro(bairro));
            model.addAttribute("bairroFiltro", bairro);
        } else {
            model.addAttribute("pontos", pontoApoioService.listarTodos());
        }
        return "pontos/lista";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("ponto", new PontoApoioDTO());
        model.addAttribute("tiposPonto", TipoPontoApoio.values());
        return "pontos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("ponto") PontoApoioDTO dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("tiposPonto", TipoPontoApoio.values());
            return "pontos/form";
        }

        if (dto.getId() == null) {
            pontoApoioService.salvar(dto);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Ponto de apoio cadastrado com sucesso!");
        } else {
            pontoApoioService.atualizar(dto.getId(), dto);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Ponto de apoio atualizado com sucesso!");
        }

        return "redirect:/pontos";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        PontoApoioDTO dto = pontoApoioService.buscarPorId(id);
        model.addAttribute("ponto", dto);
        model.addAttribute("tiposPonto", TipoPontoApoio.values());
        return "pontos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pontoApoioService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Ponto de apoio removido com sucesso!");
        return "redirect:/pontos";
    }
}
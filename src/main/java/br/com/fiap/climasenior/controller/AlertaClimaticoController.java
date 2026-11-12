package br.com.fiap.climasenior.controller;

import br.com.fiap.climasenior.dto.AlertaClimaticoDTO;
import br.com.fiap.climasenior.model.enums.NivelAlerta;
import br.com.fiap.climasenior.model.enums.StatusAlerta;
import br.com.fiap.climasenior.service.AlertaClimaticoService;
import br.com.fiap.climasenior.service.PontoApoioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alertas")
public class AlertaClimaticoController {

    private final AlertaClimaticoService alertaService;
    private final PontoApoioService pontoApoioService;

    public AlertaClimaticoController(AlertaClimaticoService alertaService,
                                     PontoApoioService pontoApoioService) {
        this.alertaService = alertaService;
        this.pontoApoioService = pontoApoioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String bairro,
                         @RequestParam(required = false) Boolean apenasAtivos,
                         Model model) {
        if (Boolean.TRUE.equals(apenasAtivos)) {
            model.addAttribute("alertas", alertaService.listarAtivos());
            model.addAttribute("filtroAtivos", true);
        } else if (bairro != null && !bairro.isBlank()) {
            model.addAttribute("alertas", alertaService.buscarPorBairro(bairro));
            model.addAttribute("bairroFiltro", bairro);
        } else {
            model.addAttribute("alertas", alertaService.listarTodos());
        }
        return "alertas/lista";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("alerta", new AlertaClimaticoDTO());
        carregarCombos(model);
        return "alertas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("alerta") AlertaClimaticoDTO dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            carregarCombos(model);
            return "alertas/form";
        }

        if (dto.getId() == null) {
            alertaService.salvar(dto);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Alerta climático emitido com sucesso!");
        } else {
            alertaService.atualizar(dto.getId(), dto);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Alerta climático atualizado com sucesso!");
        }

        return "redirect:/alertas";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        AlertaClimaticoDTO dto = alertaService.buscarPorId(id);
        model.addAttribute("alerta", dto);
        carregarCombos(model);
        return "alertas/form";
    }

    @GetMapping("/resolver/{id}")
    public String resolver(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        alertaService.resolverAlerta(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Alerta marcado como resolvido!");
        return "redirect:/alertas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        alertaService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Alerta climático removido com sucesso!");
        return "redirect:/alertas";
    }

    private void carregarCombos(Model model) {
        model.addAttribute("niveisAlerta", NivelAlerta.values());
        model.addAttribute("statusAlerta", StatusAlerta.values());
        model.addAttribute("pontosApoio", pontoApoioService.listarAtivos());
    }
}
package br.com.fiap.climasenior.controller;

import br.com.fiap.climasenior.dto.TelemetriaClimaticaDTO;
import br.com.fiap.climasenior.service.PontoApoioService;
import br.com.fiap.climasenior.service.TelemetriaClimaticaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/telemetria")
public class TelemetriaClimaticaController {

    private final TelemetriaClimaticaService telemetriaService;
    private final PontoApoioService pontoApoioService;

    public TelemetriaClimaticaController(TelemetriaClimaticaService telemetriaService,
                                         PontoApoioService pontoApoioService) {
        this.telemetriaService = telemetriaService;
        this.pontoApoioService = pontoApoioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Long pontoId, Model model) {
        if (pontoId != null) {
            model.addAttribute("leituras", telemetriaService.listarPorPontoApoio(pontoId));
            model.addAttribute("pontoFiltroId", pontoId);
        } else {
            model.addAttribute("leituras", telemetriaService.listarTodas());
        }
        model.addAttribute("pontosApoio", pontoApoioService.listarAtivos());
        return "telemetria/lista";
    }

    @GetMapping("/nova")
    public String exibirFormularioRegistro(Model model) {
        model.addAttribute("leitura", new TelemetriaClimaticaDTO());
        model.addAttribute("pontosApoio", pontoApoioService.listarAtivos());
        return "telemetria/form";
    }

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute("leitura") TelemetriaClimaticaDTO dto,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pontosApoio", pontoApoioService.listarAtivos());
            return "telemetria/form";
        }

        telemetriaService.registrarLeitura(dto);

        if (dto.getTemperatura() >= 35.0 || dto.getUmidade() <= 20.0) {
            redirectAttributes.addFlashAttribute("mensagemAlerta",
                    "Leitura registrada! Atenção: índices críticos detectados — um alerta climático automático foi emitido.");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Leitura climática registrada com sucesso!");
        }

        return "redirect:/telemetria";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        telemetriaService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Registro de telemetria removido com sucesso!");
        return "redirect:/telemetria";
    }
}
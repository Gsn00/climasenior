package br.com.fiap.climasenior.controller;

import br.com.fiap.climasenior.service.AlertaClimaticoService;
import br.com.fiap.climasenior.service.CidadaoService;
import br.com.fiap.climasenior.service.PontoApoioService;
import br.com.fiap.climasenior.service.TelemetriaClimaticaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PontoApoioService pontoApoioService;
    private final CidadaoService cidadaoService;
    private final AlertaClimaticoService alertaService;
    private final TelemetriaClimaticaService telemetriaService;

    public HomeController(PontoApoioService pontoApoioService,
                          CidadaoService cidadaoService,
                          AlertaClimaticoService alertaService,
                          TelemetriaClimaticaService telemetriaService) {
        this.pontoApoioService = pontoApoioService;
        this.cidadaoService = cidadaoService;
        this.alertaService = alertaService;
        this.telemetriaService = telemetriaService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalPontosAtivos", pontoApoioService.contarAtivos());
        model.addAttribute("totalCidadaos", cidadaoService.contarTotal());
        model.addAttribute("totalMobilidadeReduzida", cidadaoService.contarMobilidadeReduzida());
        model.addAttribute("totalAlertasAtivos", alertaService.contarAtivos());

        model.addAttribute("alertasRecentes", alertaService.listarAtivos());
        model.addAttribute("leiturasRecentes", telemetriaService.listarTodas().stream().limit(5).toList());

        return "index";
    }
}
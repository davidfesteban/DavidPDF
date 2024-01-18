package dev.misei.html2pdf.controller.impl;

import dev.misei.html2pdf.application.PdfRenderComponent;
import dev.misei.html2pdf.controller.ApiController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static dev.misei.html2pdf.model.Constants.SELENIARM;
import static dev.misei.html2pdf.model.RenderType.TYPE_DATA;
import static dev.misei.html2pdf.model.RenderType.TYPE_URL;

/**
 * To be used with Docker. This requires that you build, compile and use docker-compose
 */
@Controller
@RequestMapping("/docker")
@Slf4j
@RequiredArgsConstructor
public class ApiDockerController implements ApiController {

    public final PdfRenderComponent renderComponent;

    @Value("${security.api.key}")
    private String apiKey;

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/generatePdfUrl")
    public void generatePdfUrl(@RequestParam String url, HttpServletResponse response) throws Exception {
        //temporary:
//        if (!Optional.ofNullable(response.getHeader("Authorization")).orElse("").equals(String.format("Bearer %s", apiKey))) {
//            response.sendError(418, "Bad Auth");
//            return;
//        }
        //
        responseWith(renderComponent.render(url, PdfRenderComponent.createLocalDriver(SELENIARM), TYPE_URL),
                response);
    }

    @PostMapping("/generatePdfHtml")
    public void generatePdfHtml(@RequestBody String htmlData, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Invoked received");
        System.out.println(apiKey);
        //temporary:
//        if (!Optional.ofNullable(request.getHeader("x-api-key")).orElse("").equals(String.format("Bearer %s", apiKey))) {
//            response.sendError(418, "Bad Auth");
//            log.info(apiKey);
//            log.info(request.getHeader("x-api-key"));
//            log.info("Bearer {}", apiKey);
//            return;
//        }
        log.info("Passed Auth");
        responseWith(renderComponent.render(htmlData, PdfRenderComponent.createLocalDriver(SELENIARM), TYPE_DATA),
                response);
    }

}

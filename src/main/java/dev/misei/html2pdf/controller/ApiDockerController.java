package dev.misei.html2pdf.controller;

import dev.misei.html2pdf.application.PdfRenderComponent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * To be used with Docker. This requires that you build, compile and use docker-compose
 */
@Controller
@RequestMapping("/docker")
@RequiredArgsConstructor
public class ApiDockerController implements ApiController {

    public static final String SELENIARM = "http://seleniarm:4444";
    public final PdfRenderComponent urlComponent;
    public final PdfRenderComponent dataComponent;

    @Value("${security.api.key}")
    private String apiKey;

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/generatePdfUrl")
    public void generatePdfUrl(@RequestParam String url, HttpServletResponse response) throws Exception {
        //temporary:
        if (!Optional.ofNullable(response.getHeader("Authorization")).orElse("").equals(String.format("Bearer %s", apiKey))) {
            response.sendError(418, "Bad Auth");
            return;
        }
        //
        responseWith(urlComponent.render(url, PdfRenderComponent.createRemoteDriver(SELENIARM)),
                response);
    }

    @PostMapping("/generatePdfHtml")
    public void generatePdfHtml(@RequestBody String htmlData, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Invoked received");
        System.out.println(apiKey);
        //temporary:
        if (!Optional.ofNullable(request.getHeader("x-api-key")).orElse("").equals(String.format("Bearer %s", apiKey))) {
            response.sendError(418, "Bad Auth");
            System.out.println(apiKey);
            System.out.println(request.getHeader("x-api-key"));
            System.out.println(String.format("Bearer %s", apiKey));
            return;
        }
        System.out.println("Passed Auth");
        responseWith(dataComponent.render(htmlData, PdfRenderComponent.createRemoteDriver(SELENIARM)),
                response);
    }

}

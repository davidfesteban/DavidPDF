package dev.misei.html2pdf.controller;

import dev.misei.html2pdf.application.PdfRenderComponent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

/**
 * To be used with Docker. This requires that you build, compile and use docker-compose
 */
@Controller
@RequestMapping("/docker")
@RequiredArgsConstructor
public class ApiDockerController implements ApiController {

    public static final String SELENIARM = "http://localhost:4444";
    public final PdfRenderComponent urlComponent;
    public final PdfRenderComponent dataComponent;
    @Value("${security.api.key}")
    private String apiKey;

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

    @GetMapping("/generatePdfHtml")
    public void generatePdfHtml(@RequestBody String htmlData, HttpServletResponse response) throws Exception {
        //temporary:
        if (Optional.ofNullable(response.getHeader("Authorization")).orElse("").equals(String.format("Bearer %s", apiKey))) {
            response.sendError(418, "Bad Auth");
            return;
        }
        responseWith(dataComponent.render(htmlData, PdfRenderComponent.createRemoteDriver(SELENIARM)),
                response);
    }

}

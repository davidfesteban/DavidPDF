package dev.misei.html2pdf.controller;

import dev.misei.html2pdf.application.PdfRenderComponent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * To be used with Docker. This requires that you build, compile and use docker-compose
 */
@Controller
@RequestMapping("/docker")
@AllArgsConstructor
public class ApiDockerController implements ApiController {

    public static final String SELENIARM = "http://seleniarm:4444";
    public final PdfRenderComponent urlComponent;
    public final PdfRenderComponent dataComponent;

    @GetMapping("/generatePdfUrl")
    public void generatePdfUrl(@RequestParam String url, HttpServletResponse response) throws Exception {
        responseWith(urlComponent.render(url, PdfRenderComponent.createRemoteDriver(SELENIARM)),
                response);
    }

    @GetMapping("/generatePdfHtml")
    public void generatePdfHtml(@RequestBody String htmlData, HttpServletResponse response) throws Exception {
        responseWith(dataComponent.render(htmlData, PdfRenderComponent.createRemoteDriver(SELENIARM)),
                response);
    }

}

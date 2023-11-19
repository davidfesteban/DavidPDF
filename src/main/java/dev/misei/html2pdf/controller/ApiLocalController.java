package dev.misei.html2pdf.controller;

import dev.misei.html2pdf.application.PdfRenderComponent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

/**
 * To be used locally. This requires that you double click on the chromedriver and that this driver is compatible
 * with your Chrome version
 */
@Controller
@RequestMapping("/local")
@AllArgsConstructor
public class ApiLocalController implements ApiController {

    public final PdfRenderComponent urlComponent;
    public final PdfRenderComponent dataComponent;
    public final File file = new File("chromedriver");

    @GetMapping("/generatePdfUrl")
    public void generatePdfUrl(@RequestParam String url, HttpServletResponse response) throws Exception {
        responseWith(urlComponent.render(url, PdfRenderComponent.createLocalDriver(file.getAbsolutePath())),
                response);
    }

    @GetMapping("/generatePdfHtml")
    public void generatePdfHtml(@RequestBody String htmlData, HttpServletResponse response) throws Exception {
        responseWith(dataComponent.render(htmlData, PdfRenderComponent.createLocalDriver(file.getAbsolutePath())),
                response);
    }

}

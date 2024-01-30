package de.ace.html2pdf.controller.v1;

import de.ace.html2pdf.model.Constants;
import de.ace.html2pdf.model.RenderType;
import de.ace.html2pdf.application.PdfRenderComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * To be used with Docker. This requires that you build, compile and use docker-compose
 */
@Slf4j
@RestController
@RequestMapping("/pdf")
@RequiredArgsConstructor
public class PdfGenerationController {

    public final PdfRenderComponent renderComponent;

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/url")
    public ResponseEntity<byte[]> generatePdfUrl(@RequestParam String url) throws MalformedURLException, URISyntaxException {
        return new ResponseEntity<>(renderComponent.render(url, PdfRenderComponent.createRemoteDriver(Constants.getWebDriverPath()), RenderType.TYPE_URL), pdfContentTypeHeader(), HttpStatus.OK);
    }

    @PostMapping("/html")
    public ResponseEntity<byte[]> generatePdfHtml(@RequestBody String htmlData) throws MalformedURLException, URISyntaxException {
        return new ResponseEntity<>(renderComponent.render(htmlData, PdfRenderComponent.createRemoteDriver(Constants.getWebDriverPath()), RenderType.TYPE_DATA), pdfContentTypeHeader(), HttpStatus.OK);
    }

    private HttpHeaders pdfContentTypeHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        return httpHeaders;
    }
}

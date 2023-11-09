package de.ace.backend.html2pdf;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@AllArgsConstructor
public class ApiController {

    public final HtmlPdfComponent urlComponent;
    public final HtmlPdfComponent dataComponent;

    @GetMapping("/generatePdfUrl")
    public void generatePdfUrl(@RequestParam String url, HttpServletResponse response) throws Exception {
        responseWith(urlComponent.render(url, "/Users/davidfesteban/Repositories/HTML2PDF/chromedriver"),
                response);
    }

    @GetMapping("/generatePdfHtml")
    public void generatePdfHtml(@RequestBody String htmlData, HttpServletResponse response) throws Exception {
        responseWith(dataComponent.render(htmlData, "/Users/davidfesteban/Repositories/HTML2PDF/chromedriver"),
                response);
    }

    private void responseWith(byte[] renderedPdf, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=output.pdf");

        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             OutputStream responseStream = response.getOutputStream()) {
            byteStream.write(renderedPdf);
            byteStream.writeTo(responseStream);
        }
    }

}

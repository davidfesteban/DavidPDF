package de.ace.backend.html2pdf;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

@Controller
@AllArgsConstructor
public class ApiController {

    public final HtmlPdfComponent urlComponent;
    public final HtmlPdfComponent dataComponent;
    public final File file = new File("chromedriver");

    @GetMapping("/generatePdfUrl")
    public void generatePdfUrl(@RequestParam String url, HttpServletResponse response) throws Exception {
        responseWith(urlComponent.render(url, file.getAbsolutePath()),
                response);
    }

    @GetMapping("/generatePdfHtml")
    public void generatePdfHtml(@RequestBody String htmlData, HttpServletResponse response) throws Exception {
        responseWith(dataComponent.render(htmlData, file.getAbsolutePath()),
                response);
    }

    @GetMapping("/generatePdfHtmlDocker")
    public void generatePdfHtmlDocker(@RequestBody String htmlData, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=output.pdf");
        //System.setProperty("webdriver.chrome.driver", "http://localhost:3000/webdriver");

        ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments("--headless", "--no-sandbox");

        RemoteWebDriver driver = new RemoteWebDriver(new URI("http://seleniarm:4444").toURL(), chromeOptions);
        //ChromeDriver driver = new ChromeDriver(chromeOptions);

        driver.get("data:text/html," + UriEncoder.encode(htmlData));

        Thread.sleep(2000);
        PrintOptions printOptions = new PrintOptions();
        printOptions.setBackground(true);
        var pdf = driver.print(printOptions);

        driver.quit();
        //driver.quit(); Causes crash. We need a better way to clean up memory

        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             OutputStream responseStream = response.getOutputStream()) {
            byteStream.write(java.util.Base64.getDecoder().decode(pdf.getContent()));
            byteStream.writeTo(responseStream);
        }
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

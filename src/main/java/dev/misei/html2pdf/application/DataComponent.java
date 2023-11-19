package dev.misei.html2pdf.application;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.UriEncoder;

@Component
public class DataComponent implements PdfRenderComponent {
    @Override
    public void renderProcess(WebDriver driver, String data) throws InterruptedException {
        driver.get("data:text/html," + UriEncoder.encode(data));
        //This is a joke
        Thread.sleep(1000);
    }
}

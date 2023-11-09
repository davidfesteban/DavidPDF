package de.ace.backend.html2pdf;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.UriEncoder;

@Component
public class DataComponent implements HtmlPdfComponent {
    @Override
    public void renderProcess(ChromeDriver driver, String data) throws InterruptedException {
        driver.get("data:text/html," + UriEncoder.encode(data));
        //This is a joke
        Thread.sleep(1000);
    }
}

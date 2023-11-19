package dev.misei.html2pdf.application;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class UrlComponent implements PdfRenderComponent {
    @Override
    public void renderProcess(WebDriver driver, String data) throws InterruptedException {
        driver.get(data);
        //This is a joke
        Thread.sleep(1000);
    }
}

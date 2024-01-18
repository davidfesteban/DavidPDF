package dev.misei.html2pdf.application.impl;

import dev.misei.html2pdf.application.PdfRenderComponent;
import dev.misei.html2pdf.model.RenderType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.UriEncoder;

import java.time.Duration;

import static dev.misei.html2pdf.model.Constants.WEBDRIVER_WAIT_TIMEOUT_IN_SECONDS;

@Component
public class PdfRenderComponentImpl implements PdfRenderComponent {
    @Override
    public void renderProcess(WebDriver driver, String data, RenderType renderType) {
        driver.get(getFormattedDataByInputType(data, renderType));
        new WebDriverWait(driver, Duration.ofSeconds(WEBDRIVER_WAIT_TIMEOUT_IN_SECONDS)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    private String getFormattedDataByInputType(String data, RenderType renderType) {
        switch (renderType) {
            case TYPE_DATA -> {
                return "data:text/html," + UriEncoder.encode(data);
            }
            case TYPE_URL -> {
                return data;
            }
            default -> throw new IllegalArgumentException("Wrong input data type");
        }
    }
}

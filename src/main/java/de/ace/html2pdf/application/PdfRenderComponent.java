package de.ace.html2pdf.application;

import de.ace.html2pdf.model.RenderType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;


public interface PdfRenderComponent {

    static RemoteWebDriver createRemoteDriver(String url) throws URISyntaxException, MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments("--headless", "--no-sandbox");

        return new RemoteWebDriver(new URI(url).toURL(), chromeOptions);
    }

    String render(String data, RenderType renderType, ByteArrayOutputStream byteArrayOutputStream);


    void renderProcess(WebDriver driver, String data, RenderType renderType);

}

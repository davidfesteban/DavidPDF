package dev.misei.html2pdf.application;

import dev.misei.html2pdf.model.RenderType;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public interface PdfRenderComponent {

    static ChromeDriver createLocalDriver(String chromeDriverPath) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments("--headless", "--disable-gpu",
                        "--run-all-compositor-stages-before-draw", "--remote-allow-origins=*", "--allow-http-background-page");

        return new ChromeDriver(chromeOptions);
    }

    static RemoteWebDriver createRemoteDriver(String url) throws URISyntaxException, MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments("--headless", "--no-sandbox");

        return new RemoteWebDriver(new URI(url).toURL(), chromeOptions);
    }

    default byte[] render(String data, WebDriver driver, RenderType renderType) {
        renderProcess(driver, data, renderType);

        PrintOptions printOptions = new PrintOptions();
        printOptions.setBackground(true);
        var pdf = ((PrintsPage) driver).print(printOptions);
        System.out.println("PDF generated");
        if (driver instanceof RemoteWebDriver remoteWebDriver) {
            remoteWebDriver.quit();
        }

        return java.util.Base64.getDecoder().decode(pdf.getContent());
    }

    void renderProcess(WebDriver driver, String data, RenderType renderType);

}

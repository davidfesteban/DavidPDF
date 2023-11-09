package de.ace.backend.html2pdf;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;

public interface HtmlPdfComponent {
    default byte[] render(String data, String chromeDriverPath) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments("--headless", "--disable-gpu",
                        "--run-all-compositor-stages-before-draw", "--remote-allow-origins=*", "--allow-http-background-page");
        ChromeDriver driver = new ChromeDriver(chromeOptions);

        renderProcess(driver, data);

        PrintOptions printOptions = new PrintOptions();
        printOptions.setBackground(true);
        driver.print(printOptions);

        return java.util.Base64.getDecoder().decode(driver.print(printOptions).getContent());
    }

    void renderProcess(ChromeDriver driver, String data) throws InterruptedException;

}

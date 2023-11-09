import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/davidfesteban/Repositories/ACE/backend-pdf-tool/chromedriver");

        // Create ChromeOptions and enable headless mode
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless", "--disable-gpu", "--run-all-compositor-stages-before-draw", "--remote-allow-origins=*");

        ChromeDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.ace.de/");

        try {

            String command = "Page.printToPDF";
            Map<String, Object> output = driver.executeCdpCommand(command, Map.of());

            try {
                FileOutputStream fileOutputStream = new FileOutputStream("/Users/davidfesteban/Repositories/ACE/backend-pdf-tool/target/pdf/generated.pdf");
                byte[] byteArray = java.util.Base64.getDecoder().decode((String) output.get("data"));
                fileOutputStream.write(byteArray);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw e;
        } finally {
            driver.quit();
        }
    }
}


package de.ace.html2pdf.application.impl;

import de.ace.html2pdf.application.PdfRenderComponent;
import de.ace.html2pdf.model.Constants;
import de.ace.html2pdf.model.RenderType;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static de.ace.html2pdf.model.Constants.WEBDRIVER_WAIT_TIMEOUT_IN_SECONDS;
import static java.util.Base64.getDecoder;
import static java.util.Optional.ofNullable;

@Component
public class PdfRenderComponentImpl implements PdfRenderComponent {


    private String findFooterByTag(String data, RenderType renderType) {
        Document document = renderType.getJsoupDocument(data);
        Optional<Element> optionalFooter = ofNullable(document.selectFirst("footer"));

        AtomicReference<String> result = new AtomicReference<>("");
        optionalFooter.ifPresent(footer -> result.set(footer.html()));
        return result.get();
    }


    private String getUpdatedCSS(Element cssElement) {
        String footerStyles = "\n.page-footer {\n padding: 0 70px 40px 60px;\n}\n";
        return cssElement.outerHtml().replace("</style>", footerStyles + "</style>");
    }

    private byte[] renderPdf(final String data, final WebDriver driver, final RenderType renderType) {
        renderProcess(driver, data, renderType);

        PrintOptions printOptions = new PrintOptions();
        printOptions.setBackground(true);
        var pdf = ((PrintsPage) driver).print(printOptions);
        System.out.println("PDF generated");
        if (driver instanceof RemoteWebDriver remoteWebDriver) {
            remoteWebDriver.quit();
        }

        return getDecoder().decode(pdf.getContent());
    }

    @SneakyThrows
    @Override
    public String render(String data, RenderType renderType, ByteArrayOutputStream byteArrayOutputStream) {
        byteArrayOutputStream.write(renderPdf(data, PdfRenderComponent.createRemoteDriver(Constants.getWebDriverPath()), renderType));
        return findFooterByTag(data, renderType);
    }

    @Override
    public void renderProcess(WebDriver driver, String data, RenderType renderType) {
        driver.get(renderType.getFormattedData(data));
        new WebDriverWait(driver, Duration.ofSeconds(WEBDRIVER_WAIT_TIMEOUT_IN_SECONDS))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }
}

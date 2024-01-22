package de.ace.html2pdf.application.impl;

import de.ace.html2pdf.application.PdfRenderComponent;
import de.ace.html2pdf.model.RenderType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

import static de.ace.html2pdf.model.Constants.WEBDRIVER_WAIT_TIMEOUT_IN_SECONDS;
import static java.util.Optional.ofNullable;

@Component
public class PdfRenderComponentImpl implements PdfRenderComponent {
    @Override
    public String fixFooterTag(String data, RenderType renderType) {
        Document document = renderType.getJsoupDocument(data);

        Optional<Element> optionalFooter = ofNullable(document.selectFirst("footer"));
        optionalFooter.ifPresent(footer -> footer.tagName("div").attr("class", "page-footer"));

        Optional<Element> cssElement = ofNullable(document.selectFirst("style"));
        cssElement.ifPresent(element -> element.replaceWith(Jsoup.parse(getUpdatedCSS(element))));

        return document.outerHtml();
    }

    private String getUpdatedCSS(Element cssElement) {
        String footerStyles = "\n.page-footer {\n padding: 0 70px 40px 60px;\n}\n";
        return cssElement.outerHtml().replace("</style>", footerStyles + "</style>");
    }

    @Override
    public void renderProcess(WebDriver driver, String data, RenderType renderType) {
        driver.get(renderType.getFormattedData(data));
        new WebDriverWait(driver, Duration.ofSeconds(WEBDRIVER_WAIT_TIMEOUT_IN_SECONDS))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }
}

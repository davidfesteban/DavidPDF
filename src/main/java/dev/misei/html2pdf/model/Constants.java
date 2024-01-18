package dev.misei.html2pdf.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static final String SELENIARM = "http://localhost:4444";
    public static final int WEBDRIVER_WAIT_TIMEOUT_IN_SECONDS = 5;
    public static String apiKey;

    @Autowired
    public void setApiKey(@Value("${security.api.key}") String apiKey) {
        Constants.apiKey = apiKey;
    }
}

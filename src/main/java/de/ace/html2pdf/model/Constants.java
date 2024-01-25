package de.ace.html2pdf.model;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @Getter
    private static String webDriverPath;

    @Getter
    private static String apiKey;

    public static final int WEBDRIVER_WAIT_TIMEOUT_IN_SECONDS = 5;

    public static void setWebDriverPath(String webDriverPath) {
        Constants.webDriverPath = webDriverPath;
    }

    public static void setApiKey(String apiKey) {
        Constants.apiKey = apiKey;
    }
}

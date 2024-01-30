package de.ace.html2pdf.config;

import de.ace.html2pdf.model.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationValuesConfig {

    @Autowired
    public void setApiKey(@Value("${security.api.key}") String apiKey) {
        Constants.setApiKey(apiKey);
    }

    @Autowired
    public void setWebDriverPath(@Value("${selenium.driver-path}") String path) {
        Constants.setWebDriverPath(path);
    }
}

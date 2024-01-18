package dev.misei.html2pdf.model;

import org.yaml.snakeyaml.util.UriEncoder;

public enum RenderType {
    TYPE_DATA {
        @Override
        public String getFormattedData(String data) {
            return "data:text/html," + UriEncoder.encode(data);
        }
    },
    TYPE_URL {
        @Override
        public String getFormattedData(String data) {
            return data;
        }
    };

    public abstract String getFormattedData(String data);
}

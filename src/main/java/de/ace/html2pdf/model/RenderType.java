package de.ace.html2pdf.model;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.yaml.snakeyaml.util.UriEncoder;

public enum RenderType {
    TYPE_DATA {
        @Override
        public String getFormattedData(String data) {
            return "data:text/html," + UriEncoder.encode(data);
        }

        @Override
        public Document getJsoupDocument(String data) {
            return Jsoup.parse(data);
        }
    },
    TYPE_URL {
        @Override
        public String getFormattedData(String data) {
            return data;
        }

        @SneakyThrows
        @Override
        public Document getJsoupDocument(String data) {
            return Jsoup.connect(data).ignoreContentType(true).get();
        }
    };

    public abstract String getFormattedData(String data);
    public abstract Document getJsoupDocument(String data);
}

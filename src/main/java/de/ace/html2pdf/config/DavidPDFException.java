package de.ace.html2pdf.config;

public class DavidPDFException extends RuntimeException {
    public DavidPDFException(String message) {
        super(message);
    }

    public enum Type {
        CORRUPTED_STREAM("We could not process your shit"),
        GENERIC_MARK("David you are mess");

        private final String message;

        Type(String message) {
            this.message = message;
        }

        public DavidPDFException boom() {
            return new DavidPDFException(message);
        }

    }

}

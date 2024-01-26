package de.ace.html2pdf.application;

import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostProcessorPdfComponent {

    public byte[] attachFooter(String footer, ByteArrayOutputStream pdfRenderStream) throws IOException {

        try (
                PdfReader reader = new PdfReader(pdfRenderStream.toByteArray());
                ByteArrayOutputStream pdfWithFooterStream = new ByteArrayOutputStream();
                PdfStamper stamper = new PdfStamper(reader, pdfWithFooterStream);
        ) {
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

            // Loop over each page and add a footer
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                // Get the PdfContentByte object
                PdfContentByte over = stamper.getOverContent(i);

                // Add text at the bottom of the page
                over.beginText();
                over.setFontAndSize(baseFont, 12);
                over.showTextAligned(Element.ALIGN_CENTER, footerText,
                        PageSize.A4.getWidth() / 2, 30, 0); // Position may need adjustment
                over.endText();
            }

            stamper.close();
            reader.close();

            // Return the modified PDF as a byte array
            return outputStream.toByteArray();
            return pdfWithFooterStream.toByteArray();
        }
    }

}

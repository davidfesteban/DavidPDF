package de.ace.html2pdf.application;

import de.ace.html2pdf.config.DavidPDFException;
import de.ace.html2pdf.model.RenderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfService {

    private final PdfRenderComponent pdfRenderComponent;
    private final PostProcessorPdfComponent postProcessorPdfComponent;

    public byte[] convert(String data, RenderType renderType) {
        try (ByteArrayOutputStream bs = new ByteArrayOutputStream()) {

            var footer = pdfRenderComponent.render(data, renderType, bs);
            return postProcessorPdfComponent.attachFooter(footer, bs);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw DavidPDFException.Type.CORRUPTED_STREAM.boom();
        }
    }
}

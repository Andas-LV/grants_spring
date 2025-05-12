package com.grants.spring.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class PdfReaderService {

    public static List<String> readPdfLines(MultipartFile file) throws IOException {
        List<String> lines = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            String[] splitLines = text.split("\\r?\\n");
            for (String line : splitLines) {
                lines.add(line.trim());
            }
        }

        return lines;
    }
}

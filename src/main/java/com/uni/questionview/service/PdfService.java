package com.uni.questionview.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class PdfService {
    public String readPdf(MultipartFile file) {
        String text = "";

        try {
            PDDocument document = PDDocument.load(convertToFile(file));

            PDFTextStripper pdfStripper = new PDFTextStripper();

            text = pdfStripper.getText(document);

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    public static File convertToFile(MultipartFile multipartFile) throws IOException {
        Path tempDir = Files.createTempDirectory("");

        File file = tempDir.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())).toFile();

        multipartFile.transferTo(file);

        return file;
    }
}
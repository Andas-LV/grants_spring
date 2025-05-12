package com.grants.spring.util;

import com.grants.spring.model.StudentRecord;

import java.util.ArrayList;
import java.util.List;

public class PdfParser {

    public static List<StudentRecord> parsePdfLines(List<String> lines) {
        List<StudentRecord> records = new ArrayList<>();
        String currentFaculty = null;

        for (String line : lines) {
            if (line.startsWith("В0") || line.startsWith("B")) {
                currentFaculty = line;
                continue;
            }

            if (line.isEmpty() || line.startsWith("№") || line.contains("Сумма")) {
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length < 4) {
                continue;
            }

            String code = parts[parts.length - 1];
            int sumPoints;
            try {
                sumPoints = Integer.parseInt(parts[parts.length - 2]);
            } catch (NumberFormatException e) {
                continue;
            }

            StringBuilder fioBuilder = new StringBuilder();
            for (int i = 1; i < parts.length - 2; i++) {
                fioBuilder.append(parts[i]).append(" ");
            }
            String fio = fioBuilder.toString().trim();

            StudentRecord record = new StudentRecord();
            record.setFaculty(currentFaculty);
            record.setCode(code);
            record.setFio(fio);
            record.setSumPoints(sumPoints);

            records.add(record);
        }
        return records;
    }
}

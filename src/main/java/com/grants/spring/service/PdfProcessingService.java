package com.grants.spring.service;

import com.grants.spring.model.StudentRecord;
import com.grants.spring.repository.StudentRecordRepository;
import com.grants.spring.util.PdfParser;
import com.grants.spring.util.PdfReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PdfProcessingService {

    @Autowired
    private StudentRecordRepository studentRecordRepository;

    @Autowired
    private PdfReaderService pdfReaderService;

    // Обработка PDF файла и сохранение в БД
    public void processPdf(MultipartFile file) throws IOException {
        List<String> lines = pdfReaderService.readPdfLines(file);
        List<StudentRecord> records = PdfParser.parsePdfLines(lines);
        studentRecordRepository.saveAll(records);
    }

    // Получение всех студентов, сгруппированных по факультетам с подсчетом
    public Map<String, Object> getAllStudentsWithCount() {
        List<StudentRecord> students = studentRecordRepository.findAll();
        Map<String, List<StudentRecord>> facultyGroups = new HashMap<>();

        // Группировка студентов по факультетам, исключаем null факультеты
        for (StudentRecord student : students) {
            String faculty = student.getFaculty();
            if (faculty != null) {
                facultyGroups.computeIfAbsent(faculty, k -> new ArrayList<>()).add(student);
            }
        }

        // Формируем ответ с count
        Map<String, Object> response = new HashMap<>();
        response.put("count", students.size());  // Добавляем количество студентов
        response.put("faculties", facultyGroups);  // Группировка студентов по факультетам

        return response;
    }

    // Получение студентов, сгруппированных по факультетам
    public Map<String, List<StudentRecord>> getStudentsGroupedByFaculty() {
        List<StudentRecord> students = studentRecordRepository.findAll();
        Map<String, List<StudentRecord>> facultyGroups = new HashMap<>();

        for (StudentRecord student : students) {
            String faculty = student.getFaculty();
            if (faculty != null) {
                facultyGroups.computeIfAbsent(faculty, k -> new ArrayList<>()).add(student);
            }
        }

        return facultyGroups;
    }

    // Поиск студентов по имени
    public List<StudentRecord> searchStudentsByName(String name) {
        List<StudentRecord> allStudents = studentRecordRepository.findAll();
        List<StudentRecord> result = new ArrayList<>();

        for (StudentRecord student : allStudents) {
            if (student.getFio().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }

        return result;
    }
}

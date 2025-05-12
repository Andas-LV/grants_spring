package com.grants.spring.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grants.spring.model.StudentRecord;
import com.grants.spring.service.PdfProcessingService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private PdfProcessingService pdfProcessingService;

    // Эндпоинт для загрузки и обработки файла
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            pdfProcessingService.processPdf(file);
            return ResponseEntity.ok("File processed successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }

    // Эндпоинт для получения студентов, сгруппированных по факультетам с общим количеством
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStudents() {
        Map<String, Object> studentsWithCount = pdfProcessingService.getAllStudentsWithCount();
        return ResponseEntity.ok(studentsWithCount);
    }

    // Эндпоинт для получения студентов по факультетам
    @GetMapping("/faculties")
    public ResponseEntity<Map<String, List<StudentRecord>>> getStudentsByFaculty() {
        Map<String, List<StudentRecord>> facultyGroups = pdfProcessingService.getStudentsGroupedByFaculty();
        return ResponseEntity.ok(facultyGroups);
    }

    // Эндпоинт для поиска студентов по имени
    @GetMapping("/search")
    public ResponseEntity<List<StudentRecord>> searchStudentsByName(@RequestParam("name") String name) {
        List<StudentRecord> students = pdfProcessingService.searchStudentsByName(name);
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllStudents() {
        try {
            pdfProcessingService.deleteAllStudents();  // Удаление всех студентов
            return ResponseEntity.ok("All students deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting students: " + e.getMessage());
        }
    }
}

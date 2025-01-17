package org.studymanager.main.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studymanager.main.model.student.dto.StudentRequest;
import org.studymanager.main.model.student.dto.StudentResponse;
import org.studymanager.main.security.permission.CheckAccess;
import org.studymanager.main.service.impl.StudentServiceImpl;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")
public class StudentController {
  private final StudentServiceImpl studentService;

  @CheckAccess("ADMIN")
  @GetMapping
  public ResponseEntity<List<StudentResponse>> getAll() {
    return ResponseEntity.ok(studentService.getAll());
  }

  @CheckAccess("ADMIN")
  @GetMapping("/{id}")
  public ResponseEntity<StudentResponse> getById(@PathVariable String id) {
    return ResponseEntity.ok(studentService.getById(id));
  }

  @CheckAccess("ADMIN")
  @PostMapping
  public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest studentRequest) {
    return ResponseEntity.ok(studentService.create(studentRequest));
  }

  @CheckAccess("ADMIN")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    studentService.delete(id);
    return ResponseEntity.ok("Student by ID: " + id + " was successfully deleted.");
  }

  @CheckAccess("ADMIN")
  @PutMapping("/{id}")
  public ResponseEntity<StudentResponse> update(
          @PathVariable String id,
          @RequestBody StudentRequest studentRequest
  ) {

    return ResponseEntity.ok(studentService.update(id, studentRequest));
  }
}

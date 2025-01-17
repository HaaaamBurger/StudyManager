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
import org.studymanager.main.model.teacher.TeacherRequest;
import org.studymanager.main.model.teacher.dto.TeacherResponse;
import org.studymanager.main.security.permission.CheckAccess;
import org.studymanager.main.service.impl.TeacherServiceImpl;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
  private final TeacherServiceImpl teacherService;

  @CheckAccess("ADMIN")
  @GetMapping
  public ResponseEntity<List<TeacherResponse>> getAll() {
    return ResponseEntity.ok(teacherService.getAll());
  }

  @CheckAccess("ADMIN")
  @GetMapping("/{id}")
  public ResponseEntity<TeacherResponse> getById(@PathVariable String id) {
    return ResponseEntity.ok(teacherService.getById(id));
  }

  @CheckAccess("ADMIN")
  @PostMapping
  public ResponseEntity<TeacherResponse> create(@Valid @RequestBody TeacherRequest teacherRequest) {
    return ResponseEntity.ok(teacherService.create(teacherRequest));
  }

  @CheckAccess("ADMIN")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    teacherService.delete(id);
    return ResponseEntity.ok("Teacher by ID: " + id + " was successfully deleted.");
  }

  @CheckAccess("ADMIN")
  @PutMapping("/{id}")
  public ResponseEntity<TeacherResponse> update(
          @PathVariable String id,
          @RequestBody TeacherRequest teacherRequest
  ) {

    return ResponseEntity.ok(teacherService.update(id, teacherRequest));
  }

}

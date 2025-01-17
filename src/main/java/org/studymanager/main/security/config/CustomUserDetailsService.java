package org.studymanager.main.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.studymanager.main.repository.StudentRepository;
import org.studymanager.main.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return studentRepository.readByEmail(username)
            .map(student -> (UserDetails) student)
            .orElseGet(() -> teacherRepository.readByEmail(username)
                    .map(teacher -> (UserDetails) teacher)
                    .orElseThrow(() -> new UsernameNotFoundException("User by email " + username + " wasn't found.")));
  }
}

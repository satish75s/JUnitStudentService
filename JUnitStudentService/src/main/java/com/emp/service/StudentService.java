package com.emp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Student;
import com.emp.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
    private StudentRepository studentRepository;

    
	
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

   
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

   
    public Optional<Student> updateStudent(Long id, Student student) {
        if (studentRepository.existsById(id)) {
            student.setId(id);  
            return Optional.of(studentRepository.save(student));  // Save the updated student
        }
        return Optional.empty();  
    }

    
    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

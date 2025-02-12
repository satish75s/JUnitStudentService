package com.emp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.emp.entity.Student;
import com.emp.repository.StudentRepository;

@SpringBootTest
public class StudentServiceTest1 {

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private StudentService studentService;

	private Student student;

	@BeforeEach
	void setUp() {

		student = new Student("Raghuram", "raghuram@gmail.com", "Java");
	}

	@Test
	void testSaveStudent() {

		Student savedStudent = new Student("Raghuram", "raghuram@gmail.com", "Java");
		savedStudent.setId(1L);
		when(studentRepository.save(student)).thenReturn(savedStudent);

		Student result = studentService.saveStudent(student);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Raghuram", result.getName());
		assertEquals("raghuram@gmail.com", result.getEmail());
	}

	@Test
	void testGetStudentById() {
		when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

		Optional<Student> foundStudent = studentService.getStudentById(1L);

		assertTrue(foundStudent.isPresent());
		assertEquals("Raghuram", foundStudent.get().getName());
	}

	@Test
	void testGetAllStudents() {
		Student student2 = new Student("John", "john@gmail.com", "Spring Boot");
		student2.setId(2L); 
		when(studentRepository.findAll()).thenReturn(Arrays.asList(student, student2));

		List<Student> students = studentService.getAllStudents();

		assertNotNull(students);
		assertEquals(2, students.size());
	}

	
	@Test
	void testUpdateStudent() {
		
		Student updatedStudent = new Student("Raghuram Updated", "updated@gmail.com", "Java Advanced");
		updatedStudent.setId(1L); // Set the ID for the update to work
		when(studentRepository.existsById(1L)).thenReturn(true);
		when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);

		Optional<Student> result = studentService.updateStudent(1L, updatedStudent);

		assertNotNull(result);
		assertEquals("Raghuram Updated", result.get().getName());
		assertEquals("updated@gmail.com", result.get().getEmail());
	}

	
	@Test
	void testUpdateStudentNotFound() {
		Student updatedStudent = new Student("Raghuram Updated", "updated@gmail.com", "Java Advanced");
		when(studentRepository.existsById(1L)).thenReturn(false);

		Optional<Student> result = studentService.updateStudent(1L, updatedStudent);

		assertTrue(result.isEmpty()); // Assert that the result is Optional.empty() when student is not found
	}

	// Test Delete Student
	@Test
	void testDeleteStudent() {
		when(studentRepository.existsById(1L)).thenReturn(true);

		boolean result = studentService.deleteStudent(1L);

		assertTrue(result); // Student should be deleted successfully
		verify(studentRepository, times(1)).deleteById(1L);
	}

	// Test Delete Student when not found
	@Test
	void testDeleteStudentNotFound() {
		when(studentRepository.existsById(1L)).thenReturn(false);

		boolean result = studentService.deleteStudent(1L);

		assertFalse(result); // Student does not exist
		verify(studentRepository, times(0)).deleteById(1L); // Verify that delete was not called
	}
}

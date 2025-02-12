package com.emp.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.emp.entity.Student;
import com.emp.service.StudentService;

//@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentControllerTest1.class)
public class StudentControllerTest1 {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;  // Mocking the service

    @InjectMocks
    private StudentController studentController;  // Injecting mocks into the controller

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();  // Setting up MockMvc
    }

    // Test for Create Student (POST)
    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student("John", "john@example.com", "Math");
        
        // Using when to mock the service method
        when(studentService.saveStudent(student)).thenReturn(student);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"email\":\"john@example.com\",\"course\":\"Math\"}"))
                .andExpect(status().isCreated())  // Expect HTTP status 201 (Created)
                .andExpect(jsonPath("$.name").value("John"))  // Check if the name is 'John'
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.course").value("Math"));

        // Verify if the service method was called with the correct argument
        verify(studentService).saveStudent(student);
    }

    // Test for Get All Students (GET)
    @Test
    void testGetAllStudents() throws Exception {
        Student student = new Student("John", "john@example.com", "Math");

        // Using when to mock the service method
        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(student));  // Mock the service method

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())  // Expect HTTP status 200 (OK)
                .andExpect(jsonPath("$[0].name").value("John"));

        verify(studentService).getAllStudents();  // Verify the service method is called
    }

    // Test for Get Student by ID (GET)
    @Test
    void testGetStudentById() throws Exception {
        Student student = new Student("John", "john@example.com", "Math");

        // Using when to mock the service method
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/{id}", 1L))
                .andExpect(status().isOk())  // Expect HTTP status 200 (OK)
                .andExpect(jsonPath("$.name").value("John"));

        verify(studentService).getStudentById(1L);  // Verify the service method is called
    }

    // Test for Get Student by ID - Not Found (GET)
    @Test
    void testGetStudentByIdNotFound() throws Exception {

        // Using when to mock the service method
        when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/students/{id}", 1L))
                .andExpect(status().isNotFound());  // Expect HTTP status 404 (Not Found)

        verify(studentService).getStudentById(1L);  // Verify the service method is called
    }

    // Test for Update Student (PUT)
    @Test
    void testUpdateStudent() throws Exception {
        Student existingStudent = new Student("John", "john@example.com", "Math");
        Student updatedStudent = new Student("John Updated", "john_updated@example.com", "Science");

        // Using when to mock the service method
        when(studentService.updateStudent(1L, updatedStudent)).thenReturn(Optional.of(updatedStudent));

        mockMvc.perform(put("/students/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\",\"email\":\"john_updated@example.com\",\"course\":\"Science\"}"))
                .andExpect(status().isOk())  // Expect HTTP status 200 (OK)
                .andExpect(jsonPath("$.name").value("John Updated"));

        verify(studentService).updateStudent(1L, updatedStudent);  // Verify the service method is called
    }

    // Test for Update Student - Not Found (PUT)
    @Test
    void testUpdateStudentNotFound() throws Exception {
        Student updatedStudent = new Student("John Updated", "john_updated@example.com", "Science");

        // Using when to mock the service method
        when(studentService.updateStudent(1L, updatedStudent)).thenReturn(Optional.empty());

        mockMvc.perform(put("/students/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\",\"email\":\"john_updated@example.com\",\"course\":\"Science\"}"))
                .andExpect(status().isNotFound());  // Expect HTTP status 404 (Not Found)

        verify(studentService).updateStudent(1L, updatedStudent);  // Verify the service method is called
    }

    // Test for Delete Student (DELETE)
    @Test
    void testDeleteStudent() throws Exception {
        // Using when to mock the service method
        when(studentService.deleteStudent(1L)).thenReturn(true);

        mockMvc.perform(delete("/students/{id}", 1L))
                .andExpect(status().isNoContent());  // Expect HTTP status 204 (No Content)

        verify(studentService).deleteStudent(1L);  // Verify the service method is called
    }

    // Test for Delete Student - Not Found (DELETE)
    @Test
    void testDeleteStudentNotFound() throws Exception {
        // Using when to mock the service method
        when(studentService.deleteStudent(1L)).thenReturn(false);

        mockMvc.perform(delete("/students/{id}", 1L))
                .andExpect(status().isNotFound());  // Expect HTTP status 404 (Not Found)

        verify(studentService).deleteStudent(1L);  // Verify the service method is called
    }
}


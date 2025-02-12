package com.emp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.emp.entity.Student;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest1 {

	@Autowired
	StudentRepository studentRepository;
	
	
	@BeforeEach
	void setUp() throws Exception {
		Student student=new Student("raghuram","ssk@gmail.com","java");
		studentRepository.save(student);
	}
    
	@Test
	public void testFindByName() {
		Student st=studentRepository.findByName("raghuram");
		System.out.println("mail=="+st.getEmail());
		assertEquals("ssk@gmail.com",st.getEmail());
		
	}

}

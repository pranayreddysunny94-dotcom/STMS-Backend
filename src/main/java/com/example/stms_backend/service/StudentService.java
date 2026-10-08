package com.example.stms_backend.service;

import com.example.stms_backend.entity.Student;
import com.example.stms_backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(
            StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {

        if (studentRepository.existsByRollNumber(
                student.getRollNumber())) {

            throw new RuntimeException(
                    "Roll number already exists");
        }

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"));
    }

    public Student getStudentByUserId(Long userId) {

        return studentRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found for user"));
    }

    public Student updateStudent(
            Long id,
            Student updatedStudent) {

        Student student = getStudentById(id);

        student.setRollNumber(
                updatedStudent.getRollNumber());

        student.setDepartment(
                updatedStudent.getDepartment());

        student.setYear(
                updatedStudent.getYear());

        student.setPhone(
                updatedStudent.getPhone());

        student.setAddress(
                updatedStudent.getAddress());

        student.setCollege(
                updatedStudent.getCollege());

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {

        Student student = getStudentById(id);

        studentRepository.delete(student);
    }
}
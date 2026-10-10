
package com.example.stms_backend.service;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.stms_backend.dto.LoginRequest;
import com.example.stms_backend.dto.RegisterRequest;
import com.example.stms_backend.entity.Student;
import com.example.stms_backend.entity.User;
import com.example.stms_backend.repository.StudentRepository;
import com.example.stms_backend.repository.UserRepository;
import com.example.stms_backend.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (request.getRole() == null
                || request.getRole().trim().isEmpty()) {
            throw new RuntimeException("Please select a registration role");
        }

        String role = request.getRole()
                .trim()
                .toUpperCase(Locale.ROOT);

        if (!role.equals("STUDENT") && !role.equals("TRAINER")) {
            throw new RuntimeException(
                    "Registration is allowed for Student and Trainer accounts only"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(role);

        User savedUser = userRepository.save(user);

        if ("STUDENT".equals(savedUser.getRole())) {

            Student student = new Student();

            student.setUser(savedUser);
            student.setRollNumber(generateNextRollNumber());
            student.setDepartment("AI");
            student.setYear(2026);
            student.setPhone("");
            student.setAddress("");
            student.setCollege("");

            studentRepository.save(student);
        }

        return savedUser;
    }

    private String generateNextRollNumber() {

        long studentCount = studentRepository.count();
        long nextNumber = studentCount + 1;

        return String.format(
                "23eg111a%02d",
                nextNumber
        );
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        return jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );
    }
}
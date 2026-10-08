package com.example.stms_backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.stms_backend.entity.Attendance;
import com.example.stms_backend.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:5173")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(
            AttendanceService attendanceService) {

        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<?> addAttendance(
            @RequestParam Long studentId,
            @RequestParam Long trainingProgramId,
            @RequestParam LocalDate attendanceDate,
            @RequestParam String status) {

        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            attendanceService.addAttendance(
                                    studentId,
                                    trainingProgramId,
                                    attendanceDate,
                                    status
                            )
                    );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {

        return ResponseEntity.ok(
                attendanceService.getAllAttendance()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAttendanceById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    attendanceService.getAttendanceById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAttendanceByStudent(
            @PathVariable Long studentId) {

        try {

            return ResponseEntity.ok(
                    attendanceService
                            .getAttendanceByStudent(studentId)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/program/{trainingProgramId}")
    public ResponseEntity<?> getAttendanceByProgram(
            @PathVariable Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    attendanceService
                            .getAttendanceByProgram(
                                    trainingProgramId
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}/program/{trainingProgramId}")
    public ResponseEntity<?> getAttendanceByStudentAndProgram(
            @PathVariable Long studentId,
            @PathVariable Long trainingProgramId) {

        try {

            return ResponseEntity.ok(
                    attendanceService
                            .getAttendanceByStudentAndProgram(
                                    studentId,
                                    trainingProgramId
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/user/{userId}")
public ResponseEntity<?> getAttendanceByUser(
        @PathVariable Long userId) {

    try {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByUser(userId)
        );

    } catch (RuntimeException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttendance(
            @PathVariable Long id,
            @RequestParam String status) {

        try {

            return ResponseEntity.ok(
                    attendanceService.updateAttendance(
                            id,
                            status
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendance(
            @PathVariable Long id) {

        try {

            attendanceService.deleteAttendance(id);

            return ResponseEntity.ok(
                    "Attendance deleted"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/program/{trainingProgramId}/submitted-today")
public ResponseEntity<Boolean> isAttendanceSubmittedToday(
        @PathVariable Long trainingProgramId) {

    return ResponseEntity.ok(
            attendanceService.isAttendanceSubmittedToday(
                    trainingProgramId
            )
    );
}
}
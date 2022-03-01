package cg.service;

import cg.model.Classroom;
import cg.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IStudentService {
    Page<Student> findAll(Pageable pageable);

    void save(Student student);

    void delete(Long id);

    Optional<Student> findById(Long id);

    Page<Student> findAllByClassroom(Pageable pageable, Classroom classroom);

    Page<Student> findByAvgBetweenAndNameOrAvgBetweenAndPhone(Pageable pageable, String search, double min, double max);
}

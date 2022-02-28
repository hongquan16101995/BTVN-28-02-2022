package cg.service.impl;

import cg.model.Classroom;
import cg.model.Student;
import cg.repository.IStudentRepository;
import cg.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private IStudentRepository iStudentRepository;

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return iStudentRepository.findAll(pageable);
    }

    @Override
    public void save(Student student) {
        iStudentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return iStudentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        iStudentRepository.deleteById(id);
    }

    @Override
    public Page<Student> findByClassroom(Pageable pageable, Classroom classroom) {
        return iStudentRepository.findAllByClassroom(pageable, classroom);
    }

    @Override
    public Page<Student> findByNameOrPhone(Pageable pageable, String search) {
        return iStudentRepository.findAllByNameContainingOrPhoneContaining(pageable, search, search);
    }

    @Override
    public Page<Student> findByAvgBetween(Pageable pageable, double min, double max) {
        return iStudentRepository.findAllByAvgBetween(pageable, min, max);
    }
}

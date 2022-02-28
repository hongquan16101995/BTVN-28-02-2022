package cg.service.impl;

import cg.model.Classroom;
import cg.repository.IClassroomRepository;
import cg.service.IClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassroomService implements IClassroomService {
    @Autowired
    private IClassroomRepository iClassroomRepository;

    @Override
    public Iterable<Classroom> findAll() {
        return iClassroomRepository.findAll();
    }

    @Override
    public Optional<Classroom> findById(Long id) {
        return iClassroomRepository.findById(id);
    }
}

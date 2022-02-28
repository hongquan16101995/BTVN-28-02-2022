package cg.service;

import cg.model.Classroom;

import java.util.Optional;

public interface IClassroomService {
    Iterable<Classroom> findAll();

    Optional<Classroom> findById(Long id);
}

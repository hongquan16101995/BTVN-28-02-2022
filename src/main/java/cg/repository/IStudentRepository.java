package cg.repository;

import cg.model.Classroom;
import cg.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends PagingAndSortingRepository<Student, Long> {
    Page<Student> findAllByClassroom(Pageable pageable, Classroom classroom);

    Page<Student> findAllByNameContainingOrPhoneContaining(Pageable pageable, String name, String phone);

    Page<Student> findAllByAvgBetween(Pageable pageable, double mix, double max);
}

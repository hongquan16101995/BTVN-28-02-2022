package cg.controller;

import cg.model.Classroom;
import cg.model.Student;
import cg.service.IClassroomService;
import cg.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(value = "/classroom")
public class ClassroomController {
    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IClassroomService iClassroomService;

    @ModelAttribute(name = "classes")
    private Iterable<Classroom> findAdd() {
        return iClassroomService.findAll();
    }

    @GetMapping
    public ModelAndView showListClass() {
        return new ModelAndView("classroom/list-class");
    }

    @GetMapping("/student-in")
    public ModelAndView showAll(@PageableDefault(value = 3) Pageable pageable,
                                Optional<Long> class_id) {
        ModelAndView modelAndView = new ModelAndView("classroom/student-in-class");
        Optional<Classroom> classroom = iClassroomService.findById(class_id.orElse(0L));
        Page<Student> students = iStudentService.findAllByClassroom(
                pageable, classroom.orElse(new Classroom()));
        modelAndView.addObject("class_id", class_id.orElse(0L));
        modelAndView.addObject("students", students);
        return modelAndView;
    }
}

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IClassroomService iClassroomService;

    @ModelAttribute(name = "classes")
    private Iterable<Classroom> findAdd() {
        return iClassroomService.findAll();
    }

    @GetMapping
    public ModelAndView showAll(@PageableDefault(value = 1) Pageable pageable,
                                @RequestParam(value = "search") Optional<String> search) {
        ModelAndView modelAndView = new ModelAndView("list");
        Page<Student> students;
        if (search.isPresent()) {
            students = iStudentService.findByNameOrPhone(pageable, search.get());
            modelAndView.addObject("search", search.get());
        } else {
            students = iStudentService.findAll(pageable);
        }
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @PostMapping("/avg")
    public ModelAndView showAllByAvg(@PageableDefault(value = 1) Pageable pageable,
                                @RequestParam double min, double max ) {
        ModelAndView modelAndView = new ModelAndView("list");
        Page<Student> students = iStudentService.findByAvgBetween(pageable, min, max);
        modelAndView.addObject("students", students);
        modelAndView.addObject("min", min);
        modelAndView.addObject("max", max);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Optional<Student> student = iStudentService.findById(id);
        student.ifPresent(value -> modelAndView.addObject("student", value));
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PageableDefault(value = 1) Pageable pageable, @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("list");
        iStudentService.delete(id);
        Page<Student> students = iStudentService.findAll(pageable);
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @GetMapping("/class")
    public ModelAndView showListClass() {
        return new ModelAndView("list-class");
    }

    @GetMapping("/class/{id}")
    public ModelAndView showAllStudentByClass(@PageableDefault(value = 1) Pageable pageable, @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("list");
        Optional<Classroom> classroom = iClassroomService.findById(id);
        Page<Student> students;
        if (classroom.isPresent()) {
            students = iStudentService.findByClassroom(pageable, classroom.get());
        } else {
            students = iStudentService.findAll(pageable);
        }
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("student", new Student());
        return modelAndView;
    }

//    @PostMapping("/create")
//    public ModelAndView createStudent(@PageableDefault(value = 1) Pageable pageable,
//                                      @Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView("create");
//        return getModelAndView(pageable, modelAndView, student, bindingResult);
//    }

    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return "redirect:http://localhost:8080/student/create";
        }
        iStudentService.save(student);
        return "redirect:/student";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("student", iStudentService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editStudent(@PageableDefault(value = 1) Pageable pageable,
                                    @Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("edit");
        return getModelAndView(pageable, modelAndView, student, bindingResult);
    }

    private ModelAndView getModelAndView(@PageableDefault(1) Pageable pageable, ModelAndView modelAndView,
                                         @ModelAttribute("student") @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("student", student);
            return modelAndView;
        }
        iStudentService.save(student);
        ModelAndView viewAll = new ModelAndView("list");
        Page<Student> students = iStudentService.findAll(pageable);
        viewAll.addObject("students", students);
        return viewAll;
    }
}

package com.example.demo.controller;

import com.example.demo.filter.Filter;
import com.example.demo.filter.QueryOperator;
import com.example.demo.entity.Student;
import com.example.demo.filter.FilterUtils;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.*;


@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FilterUtils filterUtils;

    @PostMapping("/save")
    public Student save(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/test")
    public List<Student> test() {

        Filter lowRange = Filter.builder()
                .field("name")
                .operator(QueryOperator.LIKE)
                .value("Alex")
                .build();
        Filter genderRange = Filter.builder()
                .field("pincode")
                .operator(QueryOperator.LIKE)
                .value("635108")
                .build();

        List<Filter> filters = new ArrayList<>();
        filters.add(lowRange);
        filters.add(genderRange);

        final Specification<Student> specificationFromFilters = getSpecificationFromFilters(filters);

        return studentRepository.findAll(specificationFromFilters);


    }

    private Specification<Student> getSpecificationFromFilters(List<Filter> filter) {
        Specification<Student> specification = where(filterUtils.createSpecification(filter.remove(0)));

        for (Filter input : filter) {
            specification = specification.and(filterUtils.createSpecification(input));
        }
        return specification;
    }

    private Specification<Student> nameLike(String name) {
        return new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    private Specification<Student> genderLike(String gender) {
        return new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("gender"), "%" + gender + "%");
            }
        };
    }
}

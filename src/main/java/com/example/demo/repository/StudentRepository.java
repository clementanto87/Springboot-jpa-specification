package com.example.demo.repository;

import com.example.demo.dto.StudentDto;
import com.example.demo.dto.StudentView;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

    //StudentDto findBy(Integer id);

    //StudentView findByName(String name);

    <T> T findByGender(String gender, Class<T> type);

}

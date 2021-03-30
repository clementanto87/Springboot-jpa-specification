package com.example.demo.dto;

import lombok.Data;

@Data
public class StudentDto {
    int id;
    String name;
    String gender;

    public StudentDto(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
}

package com.example.demo.filter;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Filter {
  private String field;
  private QueryOperator operator;
  private String value;
  private List<String> values;//Used in case of IN operator
}
package com.example.rqchallenge.mapper;

import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class MapperTest {
    @Test
    void mapToEmployee(){
        final Employee employee = Mapper.mapToEmployee(TestUtil.getEmployee());
        Assertions.assertNotNull(employee);
    }

    @Test
    void mapToEmployees(){
        final List<Employee> employees = Mapper.mapToEmployees(TestUtil.getEmployees());
        Assertions.assertEquals(3, employees.size());
    }

    @Test
    void mapToEmployees_empty(){
        final List<Employee> employees = Mapper.mapToEmployees(TestUtil.getEmployees_empty_list());
        Assertions.assertEquals(0, employees.size());
    }

    @Test
    void mapToEmployee_empty(){
        final Employee employee = Mapper.mapToEmployee(TestUtil.getEmptyEmployee());
        Assertions.assertNull(employee);
    }
}

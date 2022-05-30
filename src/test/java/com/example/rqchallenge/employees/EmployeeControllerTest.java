package com.example.rqchallenge.employees;

import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.exception.EmployeeNotFoundException;
import com.example.rqchallenge.mapper.Mapper;
import com.example.rqchallenge.service.EmployeeService;
import com.example.rqchallenge.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Test
    public void getAllEmployees_success() throws Exception {
        final List<Employee> employees = Mapper.mapToEmployees(TestUtil.getEmployees());
        when(service.getAllEmployees()).thenReturn(employees);
        this.mockMvc.perform(get("/employees")).andExpect(status().isOk());
    }

    @Test
    public void getAllEmployees_fail() throws Exception {
        final List<Employee> employees = Mapper.mapToEmployees(TestUtil.getEmployees());
        when(service.getAllEmployees()).thenThrow(RuntimeException.class);
        this.mockMvc.perform(get("/employees")).andExpect(status().is5xxServerError());
    }

    @Test
    public void getEmployeesByNameSearch_success() throws Exception {
        final List<Employee> employees = Mapper.mapToEmployees(TestUtil.getEmployees());
        when(service.searchByName("test")).thenReturn(employees);
        this.mockMvc.perform(get("/search/test")).andExpect(status().isOk());
    }

    @Test
    public void getEmployeesByNameSearch_fail() throws Exception {
        when(service.searchByName("test")).thenThrow(RuntimeException.class);
        this.mockMvc.perform(get("/search/test")).andExpect(status().is5xxServerError());
    }

    @Test
    public void getEmployeeById_success() throws Exception {
        final Employee employee = Mapper.mapToEmployee(TestUtil.getEmployee());
        when(service.findEmployeesById("1")).thenReturn(employee);
        this.mockMvc.perform(get("/1")).andExpect(status().isOk());
    }

    @Test
    public void getEmployeeById_fail() throws Exception {
        when(service.findEmployeesById("1")).thenThrow(EmployeeNotFoundException.class);
        this.mockMvc.perform(get("/1")).andExpect(status().is4xxClientError());
    }

    @Test
    public void getHighestSalaryOfEmployees_success() throws Exception {
        when(service.getHighestSalaryOfEmployees()).thenReturn(322222);
        this.mockMvc.perform(get("/highestSalary")).andExpect(status().isOk());
    }

    @Test
    public void getHighestSalaryOfEmployees_fail() throws Exception {
        when(service.getHighestSalaryOfEmployees()).thenThrow(RuntimeException.class);
        this.mockMvc.perform(get("/highestSalary")).andExpect(status().is5xxServerError());
    }

    @Test
    public void topTenHighestEarningEmployeeNames_success() throws Exception {
        when(service.getTop10HighestEarningEmployeeNames()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/topTenHighestEarningEmployeeNames")).andExpect(status().isOk());
    }

    @Test
    public void topTenHighestEarningEmployeeNames_fail() throws Exception {
        when(service.getTop10HighestEarningEmployeeNames()).thenThrow(RuntimeException.class);
        this.mockMvc.perform(get("/topTenHighestEarningEmployeeNames")).andExpect(status().is5xxServerError());
    }
}

package com.example.rqchallenge.client;

import com.example.rqchallenge.exception.EmployeeNotFoundException;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class DummyClient {

    private static final Logger LOGGER = LogManager.getLogger(DummyClient.class);
    public static final String DELETE = "delete/";
    public static final String CREATE = "create";
    public static final String EMPLOYEE = "employee/";
    final static String EMPLOYEES = "employees";

    @Autowired
    RestTemplate restTemplate;
    @Value("${dummy.client.url}")
    String dummyClientUrl;

    public String getAllEmployees(){
        try {
        final ResponseEntity<String> response =
                restTemplate.getForEntity(dummyClientUrl + EMPLOYEES, String.class);
                return response.getBody();
        }catch (HttpStatusCodeException e){
            LOGGER.error("From dummy client with status : {}, message : {}",e.getStatusCode(),
                    e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getEmployeeById(String employeeId) {
        try {
            ResponseEntity<String> response
                    = restTemplate.getForEntity(dummyClientUrl+   EMPLOYEE + employeeId, String.class);
            return response.toString();
        }catch (HttpStatusCodeException e){
            LOGGER.error("From dummy client with status : {}, message : {}",e.getStatusCode(),
                    e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public String saveEmployee(Map<String,Object> map) {
        try {
            ResponseEntity<String> response
                    = restTemplate.postForEntity(dummyClientUrl+ CREATE, map, String.class);
            return response.toString();
        }catch (HttpStatusCodeException e){
            LOGGER.error("From dummy client with status : {}, message : {}",e.getStatusCode(),
                    e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteEmployeeById(String employeeId) {
        try {
            restTemplate.delete(dummyClientUrl + DELETE +employeeId, String.class);
        }catch (HttpStatusCodeException e){
            LOGGER.error("From dummy client with status : {}, message : {}",e.getStatusCode(),
                    e.getMessage());
            if(e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new EmployeeNotFoundException(employeeId);
            throw new RuntimeException(e.getMessage());
        }
    }

}

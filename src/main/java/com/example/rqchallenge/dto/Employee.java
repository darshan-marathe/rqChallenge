package com.example.rqchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Employee implements Comparable<Employee>{
    int id;
    String name;
    int salary;
    int age;
    String profileImage;

    public Employee(int id, String name, int salary, int age, String profileImage) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.profileImage = profileImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public String getProfileImage() {
        return profileImage;
    }

    @Override
    public int compareTo(Employee e) {
        return e.getSalary() - this.salary;
    }
}

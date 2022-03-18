package com.example.demo.modelsDB;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Getter
@Setter
@ToString
@Entity
@Table(name = "employees", indexes = {
        @Index(name = "employees_employee_email_key", columnList = "employee_email", unique = true),
        @Index(name = "employees_employee_name_key", columnList = "employee_name", unique = true)
})
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Integer empId;

    @Column(name = "employee_name", nullable = false, length = 20)
    private String employeeName;

    @Column(name = "employee_email", nullable = false, length = 40)
    private String employeeEmail;

    @Column(name = "pass_word", nullable = false, length = 20)
    private String passWord;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false, length = 30)
    private RolesType roles;

    public Employees() {
    }

    public Employees(Integer empId, String employeeName, String employeeEmail, String passWord, RolesType roles) {
        this.empId = empId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.passWord = passWord;
        this.roles = roles;
    }

    public Employees(int i, String s, String xdjdjd, String s1, String password, RolesType employee) {
    }

}
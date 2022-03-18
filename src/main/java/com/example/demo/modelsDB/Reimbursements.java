package com.example.demo.modelsDB;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Getter
@Setter
@ToString
@Entity
@Table(name = "reimbursements")
public class Reimbursements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reim_request_id", nullable = false)
    private Integer reqId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reim_emp_id")
    private Employees empId;

    @Column(name = "dates", nullable = false, length = 20)
    private String dates;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Reimbursements() {
    }

    public Reimbursements(Integer reqId, Employees empId, String dates, String status, Integer amount) {
        this.reqId = reqId;
        this.empId = empId;
        this.dates = dates;
        this.status = status;
        this.amount = amount;
    }

    public Reimbursements(Employees empId, String dates, String status, Integer amount) {
        this.empId = empId;
        this.dates = dates;
        this.status = status;
        this.amount = amount;
    }
    public Reimbursements(Integer reqId, Employees empId, String status) {
        this.reqId = reqId;
        this.empId = empId;
        this.status = status;
    }

    public Reimbursements(Reimbursements reqId, Reimbursements empId, String status) {
    }

    public Reimbursements(int reqId, int i, String dates, String approved, int amount) {
    }

    public Reimbursements(int id, int i, String s, String approved, RolesType employee) {
    }


}
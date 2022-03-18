package com.example.demo.dto;

import com.example.demo.modelsDB.Employees;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReimbDTO implements Serializable {
    private int reqId;
    private int empID;
    private String dates;
    private String status;
    private int amount;

    public ReimbDTO(Integer reqId, Employees empId, String dates, String status, Integer amount) {
    }

      @Override
    public String toString() {
        return "ReimbDTO{" +
                "reqId=" + reqId +
                ", empID=" + empID +
                ", dates='" + dates + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
    }
}
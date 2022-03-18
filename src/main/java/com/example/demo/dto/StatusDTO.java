package com.example.demo.dto;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class StatusDTO implements Serializable {

    private int reqId;
    private int empId;
    private String status;


}
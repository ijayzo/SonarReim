package com.example.demo.dto;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class CreateReimReqDTO implements Serializable {


    private int reimEmpId;
    private String date;
    private String status;
    private int amount;

}
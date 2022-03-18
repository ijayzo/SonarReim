package com.example.demo.dto;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class AllReimbDTO {
    private List<ReimbDTO> reimbDTOList;
}
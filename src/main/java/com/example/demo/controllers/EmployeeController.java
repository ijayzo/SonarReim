package com.example.demo.controllers;

import com.example.demo.dto.AllReimbDTO;
import com.example.demo.dto.CreateReimReqDTO;
import com.example.demo.services.ReimbursementServices;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    int port;
    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private ReimbursementServices reimbursementServices;

    @Autowired
    public EmployeeController(ReimbursementServices reimbursementServices) {
        this.reimbursementServices = reimbursementServices;
    }
    @GetMapping("/get-req-by-empid/{empID}")
    public ResponseEntity<?> getAllReimRequests(@PathVariable Integer empId) {
        logger.info("Getting all reimbursement requests by Employee ID.");

        AllReimbDTO allReq = reimbursementServices.getAllReimbsByEmpId(empId);

        if (allReq.getReimbDTOList().isEmpty()) {
            logger.error("There have been no reimbursements nor requests made.");
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(allReq);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "newrequest")
    public ResponseEntity<?> createNewRequest(@RequestBody CreateReimReqDTO createRequestDTO) throws URISyntaxException {
        reimbursementServices.createRequest(createRequestDTO);
        logger.info("Creating new Reimbursement Request");

        return ResponseEntity.created(new URI("http://localhost:" + port + "/newrequest/created")).build();
    }

}
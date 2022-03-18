package com.example.demo.controllers;

import com.example.demo.dto.AllReimbDTO;
import com.example.demo.dto.StatusDTO;
import com.example.demo.modelsDB.Reimbursements;
import com.example.demo.services.ReimbursementServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Arrays;

// As a Manager I can:
//View all reimbursements
//Approve/Deny/Reassign reimbursements

/**
 * two layers of authentication
 *      1) only managers can access this
 *      2) if pending, then manager can't update
 * missing email to reim_man; to tell them that they have a new pending request
 */
@RestController
@RequestMapping("manager")
public class ManagerController {
    @Value("${api.config.api2URL:http://localhost:8081/api2}")
    String url;



    private ReimbursementServices reimbursementServices;
    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private RestTemplate restTemplate;

    @Autowired
    public ManagerController(ReimbursementServices reimbursementServices, RestTemplate restTemplate) {
       this.reimbursementServices = reimbursementServices;
        this.restTemplate = restTemplate;
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

    @PatchMapping(consumes = MediaType.TEXT_PLAIN_VALUE, path = "status-update/{reqid}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer reqid, @RequestBody String status) throws URISyntaxException {
        Reimbursements statusUpdate = reimbursementServices.getReimbByReqId(reqid);
        StatusDTO dto = new StatusDTO();
        dto.setStatus(status);
        dto.setReqId(reqid);

        if (!status.equals("Approved") && !status.equals("Denied") && !status.equals("Pending")) {
            logger.error("Our system only recognizes Approved, Denied, or Pending as status.");
            return ResponseEntity.ok("Please try again with appropriate status.");
        }
        if (statusUpdate.getStatus().equals("Approved")) {
            logger.error("An approved request cannot be changed.");
            return ResponseEntity.ok("Request has already been Approved!");
        }
        if (statusUpdate.getStatus().equals("Denied")) {
            logger.error("A petition to change a denied request must be sent to the Reimbursement Manager.");
            return ResponseEntity.ok("Request is denied, to resubmit request, Manager must reassign!");
        }
        if (statusUpdate.getStatus().equals("Pending")) {
            logger.error("A pending request must be sent to the Reimbursement Manager.");
            return ResponseEntity.ok("Request is pending, please reassign to Reimbursement manager.");
        }

        if (status.equals("Denied")) {
            statusUpdate.setStatus("Denied");
            reimbursementServices.updateStatus(dto);
            logger.info("Manager has denied the Reimbursement");

            reimbursementServices.updateStatus(dto);
            logger.info("Updated status to denied");

        } else if (status.equals("Approved")) {
            statusUpdate.setStatus("Approved");
            reimbursementServices.updateStatus(dto);
            logger.info("Manager has approved the Reimbursement");

            reimbursementServices.updateStatus(dto);
            logger.info("Updated status ");
        } else {
            statusUpdate.setStatus("Pending");
            reimbursementServices.updateStatus(dto);
            logger.info("Manager has changed the status to pending, only the reimbursement manager can change a 'Pending' status");

            reimbursementServices.updateStatus(dto);
            logger.info("Updated status to Pending, this *should* proc email to reimbursement manager to review the reimbursement request");
        }
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, Arrays.asList(statusUpdate.getEmpId().getEmployeeEmail(), statusUpdate.getAmount().toString(), statusUpdate.getStatus()), null);


        if (responseEntity.getStatusCode().is5xxServerError()) {
            logger.error("Email did not send");
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(null);
    }

}
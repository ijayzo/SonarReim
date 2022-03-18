package com.example.demo.services;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.ReimbursementRepository;
import com.example.demo.dto.AllReimbDTO;
import com.example.demo.dto.CreateReimReqDTO;
import com.example.demo.dto.ReimbDTO;
import com.example.demo.dto.StatusDTO;
import com.example.demo.modelsDB.Employees;
import com.example.demo.modelsDB.Reimbursements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReimbursementServices {
    private EmployeeRepository employeeRepository;
    private ReimbursementRepository reimbursementRepository;
    private List<Reimbursements> reimbursementsList;
    private List<Employees> employeesList;

    @Autowired
    public ReimbursementServices(EmployeeRepository employeeRepository, ReimbursementRepository reimbursementRepository) {
        this.employeeRepository = employeeRepository;
        this.reimbursementRepository = reimbursementRepository;
    }

    public ReimbursementServices() {

    }

    public AllReimbDTO getAllReimbsByEmpId(Integer empId) {
        List<Reimbursements> allReimbursements = reimbursementRepository.getAllByEmpId(empId);
        List<ReimbDTO> reimbDTO = allReimbursements.stream().map(
                r -> new ReimbDTO(r.getReqId(), r.getEmpId(), r.getDates(), r.getStatus(), r.getAmount())).collect(Collectors.toList());
        return new AllReimbDTO(reimbDTO);
    }

    public int createRequest(CreateReimReqDTO newReqDTO) {
        Employees employee = employeeRepository.getById(newReqDTO.getReimEmpId());
        String status = "pending";
        Reimbursements newReimReq = new Reimbursements(employee, newReqDTO.getDate(), status, newReqDTO.getAmount());
        return reimbursementRepository.save(newReimReq).getReqId();
    }

    //try to call reqid to not create
    public String updateStatus(StatusDTO newStatusDTO) {
        Reimbursements reqId = reimbursementRepository.getById(newStatusDTO.getReqId());
        Reimbursements empId = reimbursementRepository.getById(newStatusDTO.getEmpId());
        Reimbursements newReimStatus = new Reimbursements(reqId, empId, newStatusDTO.getStatus());
        return reimbursementRepository.save(newReimStatus).getStatus();
    }

    public List<Reimbursements> getAllReimbursements() {
        reimbursementsList = reimbursementRepository.findAll();
        return reimbursementsList;
    }

    public List<Employees> getAllEmployees() {
        employeesList = employeeRepository.findAll();
        return employeesList;
    }

    public Employees getEmployeeById(int id) {
        return employeeRepository.findById(id).get();
    }

    /**
     * get reimbursements by request id
     */
    public Reimbursements getReimbByReqId(int reqid) {
        return reimbursementRepository.findById(reqid).get();
    }

    //what id?
    public Reimbursements getReimbursementById(int id) {
        return reimbursementRepository.getById(id);
    }

    public void setReimbursementRepository(ReimbursementRepository reimbursementRepository) {
    }

    public void deleteEmployeeById(int i) {
    }
}
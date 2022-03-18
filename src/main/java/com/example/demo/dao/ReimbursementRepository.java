package com.example.demo.dao;

import com.example.demo.modelsDB.Reimbursements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursements, Integer> {
    //    public List<ReimbursementRepository> findAllByReqId();
    public List<Reimbursements> getAllByEmpId(Integer emp_id);

//    public List<Reimbursements> getAllByManagers_Id(Integer manager_id);
}
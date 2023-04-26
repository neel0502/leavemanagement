package com.example.leavetracker.repository;

import java.util.List;

import com.example.leavetracker.model.Leave;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends MongoRepository<Leave, String> {

	List<Leave> findByEmployeeId(String employeeId);
}

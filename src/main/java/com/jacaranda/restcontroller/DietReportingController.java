package com.jacaranda.restcontroller;

import com.jacaranda.model.DietReport;
import com.jacaranda.services.impl.DietReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
public class DietReportingController {

    @Autowired
    DietReportServiceImpl reportService;

    @GetMapping("/{username}")
    public ResponseEntity<List<DietReport>> getUserReport(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getUserReports(username));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DietReport>> getAllReports(){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getAllReports());
    }

    @PostMapping("/create-report/{username}")
    public ResponseEntity<DietReport> createReport(@PathVariable String username,
                                                           @RequestBody DietReport report) {

        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.createReport(username, report));
    }
    
    @PostMapping("/assign-report/{username}&&{id}")
    public ResponseEntity<?> assignReport(@PathVariable("username") String username, @PathVariable("id") String id){
    	return ResponseEntity.status(HttpStatus.CREATED).body(reportService.assignReport(username, Long.valueOf(id)));
    }
    
    @GetMapping("/get-assigned-reports/{username}")
    public ResponseEntity<?>getAssignedReports(@PathVariable("username") String username){
    	return ResponseEntity.status(HttpStatus.OK).body(reportService.getAssignedReports(username));
    }

}

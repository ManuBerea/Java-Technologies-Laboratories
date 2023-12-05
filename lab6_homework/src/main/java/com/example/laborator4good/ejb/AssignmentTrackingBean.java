package com.example.laborator4good.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

import static javax.ejb.ConcurrencyManagementType.BEAN;

@Singleton
@ConcurrencyManagement(BEAN)
public class AssignmentTrackingBean {
    private Map<Long, Long> assignments; // Key: ProjectId, Value: StudentId

    @PostConstruct
    public void init() {
        assignments = new HashMap<>();
    }

    public synchronized void addAssignment(Long projectId, Long studentId) {
        assignments.put(projectId, studentId);
    }

    public synchronized void removeAssignment(Long projectId)  {
        assignments.remove(projectId, assignments.get(projectId));
    }

}

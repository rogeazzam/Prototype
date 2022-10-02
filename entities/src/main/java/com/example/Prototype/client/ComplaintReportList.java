package com.example.Prototype.client;

import java.io.Serializable;
import java.util.List;

public class ComplaintReportList implements Serializable {
    List<ComplaintReport> complaints;

    public ComplaintReportList(List<ComplaintReport> complaints) {
        this.complaints = complaints;
    }

    public List<ComplaintReport> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<ComplaintReport> complaints) {
        this.complaints = complaints;
    }
}

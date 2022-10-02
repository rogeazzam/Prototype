package com.example.Prototype.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportList implements Serializable {

    private List<Report> reports=new ArrayList<Report>();

    public ReportList() {
    }

    public ReportList(List<Report> reports) {
        this.reports = reports;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}

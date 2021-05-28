package com.example.Prototype.client;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="branches")
public class BranchesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.LAZY)
    List<Branch> branches;

    public BranchesList(){
        branches=new ArrayList<Branch>();
    }
    public BranchesList(BranchesList brancheslist){
        this.branches=brancheslist.getBranches();
    }

    public List<Branch> getBranches(){
        return this.branches;
    }
    public void setBranch(Branch branch){
        this.branches.add(branch);
    }
}

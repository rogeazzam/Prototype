package com.example.Prototype.client;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="branches")
public class BranchesList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "brancheslists",
            joinColumns = { @JoinColumn(name = "brancheslists_id") },
            inverseJoinColumns = { @JoinColumn(name = "branch_id") }
    )
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

package com.example.Prototype.client;

public class BranchesListEvent {
    private BranchesList branchesList;

    public BranchesListEvent(){}

    public BranchesList getBranches(){
        return branchesList;
    }

    public BranchesListEvent(BranchesList branchesList){
        this.branchesList=branchesList;
    }
}

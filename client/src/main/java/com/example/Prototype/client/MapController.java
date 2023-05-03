package com.example.Prototype.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class MapController {

    Button seats[];

    @FXML
    private GridPane grid;

    public MapController(){
        seats=new Button[80];
    }

    public void setData(){
        int col=0,row=0;
        for(int i=0;i<80;i++){
            if(col==8){
                col=0;
                row++;
            }

            grid.add(seats[i],col++,row);
        }
    }
}

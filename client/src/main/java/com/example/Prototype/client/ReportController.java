package com.example.Prototype.client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportController {

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private GridPane grid;

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private HBox hBox;

    @FXML
    private TableView<Report> table;

    private Person user;

    private String type;

    private List<Report> reports;

    public void setData(Person user, List<Report> stats){
        this.user=user;
        this.reports=stats;
        this.stackedBarChart.setVisible(true);
        this.hBox.setVisible(false);
        String[] arr=new String[30];

        for (int i=0; i < 30; i++) {
            arr[i] = String.valueOf(i+1);
        }
        xAxis.setCategories(FXCollections.<String>observableArrayList(arr));
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();

        series1.setName("Number Of Complaints");

        int j=0;
        List<Integer> num=new ArrayList<Integer>();
        for(int i=0; i < 30; i++)
            num.add(0);

        double currentTime = new Timestamp(System.currentTimeMillis()).getTime();

        for (Report report:stats) {
            double complaintTime = new Timestamp(report.getDate().getTime()).getTime();
            double diffDays = (currentTime - complaintTime) / (60 * 60 * 1000 * 24);
            if(report.getClass().equals(ComplaintReport.class)){
                num.set((int) diffDays, num.get((int)diffDays) + 1);
            }
        }
        for(int i=0; i < 30; i ++) {
            series1.getData().add(new XYChart.Data<>(String.valueOf(i+1), num.get(i)));
        }

        stackedBarChart.getData().addAll(series1);

    }

    @FXML
    void backOP(ActionEvent event) {
        App.myStage.setScene(App.sceneStack.pop());
        App.myStage.setMaximized(true);
        App.myStage.show();
    }

    @FXML
    void complaintStatsOP(ActionEvent event) {
        setData(this.user, this.reports);
    }

    @FXML
    void otherOP(ActionEvent event) {
        hBox.setVisible(true);
        this.stackedBarChart.setVisible(false);
        this.table.getItems().clear();
        this.table.getColumns().clear();

        TableColumn nameColumn = new TableColumn("Costumer Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("costumer_name"));

        TableColumn Date= new TableColumn("Date");
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(nameColumn, Date, price);

        for(Report report:reports){
            if(report.getClass().equals(otherpurchaseReport.class)){
                table.getItems().add((otherpurchaseReport)report);
            }
        }
    }

    @FXML
    void refundOP(ActionEvent event) {
        hBox.setVisible(true);
        this.stackedBarChart.setVisible(false);
        this.table.getItems().clear();
        this.table.getColumns().clear();

        TableColumn nameColumn = new TableColumn("Costumer");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("costumer_name"));

        TableColumn Date= new TableColumn("Date");
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn Refund = new TableColumn("Refund");
        Refund.setCellValueFactory(new PropertyValueFactory<>("returnValue"));

        TableColumn branch = new TableColumn("Branch");
        branch.setCellValueFactory(new PropertyValueFactory<>("branch_name"));

        table.getColumns().addAll(nameColumn, Date, Refund, branch);

        for(Report report:reports){
            if(report.getClass().equals(RefundReport.class)){
                if(user.getClass().equals(NetworkManager.class) || (user.getClass().equals(BranchManager.class) &&
                                user.getBranch().getName().equals(((RefundReport) report).getBranch_name())))
                    table.getItems().add((RefundReport)report);
            }
        }
    }

    @FXML
    void ticketStatsOP(ActionEvent event) {
        hBox.setVisible(true);
        this.stackedBarChart.setVisible(false);
        this.table.getItems().clear();
        this.table.getColumns().clear();

        TableColumn nameColumn = new TableColumn("Costumer Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("costumer_name"));

        TableColumn Date= new TableColumn("Date");
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn branch = new TableColumn("Branch");
        branch.setCellValueFactory(new PropertyValueFactory<>("branch"));

        TableColumn hall = new TableColumn("Hall");
        hall.setCellValueFactory(new PropertyValueFactory<>("hall"));

        TableColumn seat = new TableColumn("Seat");
        seat.setCellValueFactory(new PropertyValueFactory<>("seat"));

        table.getColumns().addAll(nameColumn, Date, price, branch, hall, seat);

        for(Report report:reports){
            if(report.getClass().equals(TicketReport.class)){
                if(user.getClass().equals(NetworkManager.class) || (user.getClass().equals(BranchManager.class) &&
                        ((BranchManager)user).getBranch().getName().equals(((TicketReport)(report)).getBranch())))
                        table.getItems().add((TicketReport)report);
            }
        }
    }
}

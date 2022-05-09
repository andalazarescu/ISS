package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ArrivalTime;
import model.Employee;
import observer.Observable;
import service.Service;
import window.LoginWindow;

import java.util.List;

public class BossWindowController implements Observable {
    //connection to services
    private Service service;

    ObservableList<Employee> employeesList = FXCollections.observableArrayList();

    @FXML
    private Button logoutButton;
    @FXML
    private ListView<Employee> employeeListView;

    public BossWindowController(){
    }

    public void setService(Service service){
        this.service = service;
        service.addObservable(this);
    }

    @FXML
    public void initialize(){
        employeeListView.setItems(employeesList);
        setEmployees();
    }

    private void setEmployees() {
        var cellFactory = new Callback<ListView<Employee>, ListCell<Employee>>() {
            @Override
            public ListCell<Employee> call(ListView<Employee> param) {
                ListCell<Employee> cell = new ListCell<Employee>(){
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final HBox hBox = new HBox();
                    private final Label labelFirstName = new Label();
                    private final Label labelLastName = new Label();
                    private final Label labelTime = new Label();
                    {
                        this.setStyle("-fx-background-color: #666b6a; -fx-font-size: 16");
                        labelFirstName.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman';");
                        labelLastName.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman';");
                        labelTime.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman';");

                        hBox.setSpacing(10d);
                        hBox.getChildren().addAll(labelFirstName, labelLastName, labelTime);
                        AnchorPane.setLeftAnchor(hBox, 1d);
                        anchorPane.getChildren().add(hBox);
                    }

                    @Override
                    public void updateItem(Employee item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            ArrivalTime arrivalTime = service.findLastArrivalTimeOfEmployee(item.getID());
                            if (arrivalTime != null)
                            {
                                labelFirstName.setText(item.getFirstName());
                                labelLastName.setText(item.getLastName());
                                System.out.println(arrivalTime.toString());
                                labelTime.setText(arrivalTime.getTime().getHour() + ":" + arrivalTime.getTime().getMinute());
                            }
                            setGraphic(anchorPane);
                        }
                    }

                };
                return cell;
            }
        };
        employeeListView.setCellFactory(cellFactory);
    }

    public void showEmployees() {
        employeesList.setAll(getEmployeesList());
        employeeListView.setVisible(!employeesList.isEmpty());
    }

    private List<Employee> getEmployeesList() {
        return service.getAllEmployees();
    }

    @FXML
    public void handleLogout() throws Exception {
        Stage newWindow = (Stage) logoutButton.getScene().getWindow();
        LoginWindow main = new LoginWindow();
        main.setService(service);
        main.start(newWindow);
    }

    @Override
    public void update() {
        showEmployees();
    }
}

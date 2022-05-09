package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Employee;
import observer.Observable;
import service.Service;
import window.LoginWindow;

public class EmployeeWindowController{
    private Service service;
    private Employee employee;

    @FXML
    private Button logoutButton;

    public EmployeeWindowController(){
    }

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    public void initialize(){
    }

    @FXML
    public void handleLogout() throws Exception {
        Stage newWindow = (Stage) logoutButton.getScene().getWindow();
        LoginWindow main = new LoginWindow();
        main.setService(service);
        main.start(newWindow);
        service.logoutEmployee(employee);
        service.notifyObservables();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}

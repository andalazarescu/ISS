package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Boss;
import model.Employee;
import service.Service;
import window.BossWindow;
import window.EmployeeWindow;

import java.util.Objects;

public class LoginController {
    //connection to services
    private Service service;

    @FXML
    public RadioButton RadioAngajat;

    @FXML
    public RadioButton RadioSef;

    @FXML
    public TextField usernameTextField;

    @FXML
    public Label labelUsernameIncorrect;

    @FXML
    public Label labelPasswordIncorrect;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button loginButton;

    public LoginController(){

    }

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    public void initialize(){
        labelUsernameIncorrect.setVisible(false);
        labelPasswordIncorrect.setVisible(false);
    }

    @FXML
    public void handleLogin() throws Exception {
        if (RadioAngajat.isSelected())
            handleLoginAngajat();
        else
            handleLoginSef();
    }

    private void handleLoginSef() throws Exception {
        String username = usernameTextField.getText();
        Boss boss = service.findBossByUsername(username);
        if (boss == null)
        {
            labelUsernameIncorrect.setVisible(true);
        }
        else if (!Objects.equals(boss.getPassword(), passwordField.getText()))
        {
            labelPasswordIncorrect.setVisible(true);
        }
        else
        {
            Stage newWindow = (Stage) loginButton.getScene().getWindow();
            BossWindow bossWindow = new BossWindow();
            bossWindow.setService(service);
            bossWindow.setBoss(boss);
            bossWindow.start(newWindow);
            service.notifyObservables();
        }
    }

    private void handleLoginAngajat() throws Exception {
        String username = usernameTextField.getText();
        Employee employee = service.findEmployeeByUsername(username);
        if (employee == null)
        {
            labelUsernameIncorrect.setVisible(true);
        }
        else if (!Objects.equals(employee.getPassword(), passwordField.getText()))
        {
            labelPasswordIncorrect.setVisible(true);
        }
        else
        {
            Stage newWindow = (Stage) loginButton.getScene().getWindow();
            EmployeeWindow employeeWindow = new EmployeeWindow();
            employeeWindow.setService(service);
            employeeWindow.setEmployee(employee);
            employeeWindow.start(newWindow);
            service.loginEmployee(employee);
            service.notifyObservables();
        }
    }
    @FXML
    public void resetLabels()
    {
        labelUsernameIncorrect.setVisible(false);
        labelPasswordIncorrect.setVisible(false);
    }


}

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
import model.Requirement;
import model.RequirementStatus;
import observer.Observable;
import service.Service;
import window.LoginWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class EmployeeWindowController{
    private Service service;
    private Employee employee;
    private Requirement selectedRequirement;

    ObservableList<Requirement> requirementsList = FXCollections.observableArrayList();

    @FXML private Button logoutButton;
    @FXML private ListView<Requirement> listviewSarcini;
    @FXML private Button rezolvaSarcinaButton;

    public EmployeeWindowController(){
    }

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    public void initialize(){
        listviewSarcini.setItems(requirementsList);
        setSarcini();
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

    public void setSarcini(){
        var cellFactory = new Callback<ListView<Requirement>, ListCell<Requirement>>() {
            @Override
            public ListCell<Requirement> call(ListView<Requirement> param) {
                ListCell<Requirement> cell = new ListCell<Requirement>(){
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
                    public void updateItem(Requirement item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //ArrivalTime arrivalTime = service.findLastArrivalTimeOfRequirement(item.getID());
                            if (item != null)
                            {
                                labelFirstName.setText(item.getTitlu());
                                labelLastName.setText(item.getDescriere());
                                System.out.println(item);
                                //labelTime.setText(arrivalTime.getTime().getHour() + ":" + arrivalTime.getTime().getMinute());
                            }
                            setGraphic(anchorPane);
                        }

                        this.setOnMouseClicked(e ->
                        {
                            selectedRequirement = item;
                        });
                    }

                };
                return cell;
            }
        };
        listviewSarcini.setCellFactory(cellFactory);
    }

    public void showRequirements() {
        requirementsList.setAll(getRequirementsList());
        listviewSarcini.setVisible(!requirementsList.isEmpty());
    }

    private Collection<? extends Requirement> getRequirementsList() {
        return service.getAllRequests().stream().filter(r -> r.getStatus() == RequirementStatus.ACTIVE).collect(Collectors.toList());
    }

    public void handleRezolvaSarcina()
    {
        System.out.println(selectedRequirement.getID());
        System.out.println(selectedRequirement.getDescriere());
        service.solveRequirement(selectedRequirement);
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}

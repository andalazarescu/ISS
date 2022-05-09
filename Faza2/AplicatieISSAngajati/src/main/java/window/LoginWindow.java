package window;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import repository.ArrivalTimeDBRepo;
import repository.BossDBRepo;
import repository.EmployeeDBRepo;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoginWindow extends Application {

    private Service service;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FereastraLogin.fxml"));
            Parent root = loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Basket Games Tickets Sale");
            primaryStage.show();
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            System.out.println("Error while starting app "+e);
            alert.showAndWait();
        }
    }

    static Service getService() {
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        EmployeeDBRepo employeeDBRepo = new EmployeeDBRepo(props);
        BossDBRepo bossDBRepo = new BossDBRepo(props);
        ArrivalTimeDBRepo arrivalTimeDBRepo = new ArrivalTimeDBRepo(props);
        return new Service(employeeDBRepo, bossDBRepo, arrivalTimeDBRepo);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setService(Service service) {
        this.service = service;
    }
}

package window;

import controller.BossWindowController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.ArrivalTime;
import model.Boss;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.*;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BossWindow extends Application {

    private Service service;
    private Boss boss;
    static SessionFactory sessionFactory = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FereastraSef.fxml"));
            Parent root = loader.load();
            BossWindowController ctrl = loader.getController();
            ctrl.setService(getService());
            ctrl.showEmployees();
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
        //EmployeeDBRepo employeeDBRepo = new EmployeeDBRepo(props);
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }

        EmployeeHibernateDBRepo employeeDBRepo = new EmployeeHibernateDBRepo(sessionFactory);
        //BossHIbernateDBRepo bossDBRepo = new BossHIbernateDBRepo(sessionFactory);
        //ArrivalTimeHibernateDBRepo arrivalTimeDBRepo = new ArrivalTimeHibernateDBRepo(sessionFactory);
        //RequirementHibernateRepo requirementHibernateRepo = new RequirementHibernateRepo(sessionFactory);
        BossDBRepo bossDBRepo = new BossDBRepo(props);
        ArrivalTimeDBRepo arrivalTimeDBRepo = new ArrivalTimeDBRepo(props);
        RequirementDBRepo requirementDBRepo = new RequirementDBRepo(props);
        /*if ( sessionFactory != null ) {
            sessionFactory.close();
        }*/
        return new Service(employeeDBRepo, bossDBRepo, arrivalTimeDBRepo, requirementDBRepo);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setBoss (Boss boss)
    {
        this.boss = boss;
    }
}

package service;

import javafx.util.converter.LocalDateTimeStringConverter;
import model.ArrivalTime;
import model.ArrivalTimeStatus;
import model.Boss;
import model.Employee;
import observer.Observable;
import observer.Observer;
import repository.IArrivalTimesRepo;
import repository.IBossRepo;
import repository.IEmployeeRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Service extends Observer {
    private IEmployeeRepo employeeRepo;
    private IBossRepo bossRepo;
    private IArrivalTimesRepo arrivalTimesRepo;

    public Service(IEmployeeRepo employeeRepo, IBossRepo bossRepo, IArrivalTimesRepo arrivalTimesRepo) {
        this.employeeRepo = employeeRepo;
        this.bossRepo = bossRepo;
        this.arrivalTimesRepo = arrivalTimesRepo;
    }

    public Boss findBossByUsername(String username) {
        return bossRepo.findOneByUsername(username);
    }

    public Employee findEmployeeByUsername(String username) {
        return employeeRepo.findOneByUsername(username);
    }

    public void loginEmployee(Employee employee) {
        //ArrivalTime arrivalTime = new ArrivalTime(employee.getID(), LocalDateTime.of(2022,5,1,7,0,0), ArrivalTimeStatus.LOGGEDIN);
        ArrivalTime arrivalTime = new ArrivalTime(employee.getID(), LocalDateTime.now(), ArrivalTimeStatus.LOGGEDIN);
        arrivalTimesRepo.add(arrivalTime);
    }

    public void logoutEmployee(Employee employee) {
        List<ArrivalTime> arrivalTimes = this.findAllArrivalTimesEmployee(employee.getID());
        for (ArrivalTime arrivalTime : arrivalTimes)
        {
            arrivalTime.setStatus(ArrivalTimeStatus.LOGGEDOUT);
            arrivalTimesRepo.update(arrivalTime);
        }
    }

    public ArrivalTime findLastArrivalTimeOfEmployee(Integer id) {
        ArrayList<ArrivalTime> arrivalTimes = (ArrayList<ArrivalTime>) arrivalTimesRepo.getAll();
        LocalDateTime startHour = LocalDate.now().atTime(6, 0, 0, 0);
        List<ArrivalTime> filteredArrivalTimes = arrivalTimes.stream().filter(a -> Objects.equals(a.getIdEmployee(), id) && a.getTime().isAfter(startHour) && a.getStatus() == ArrivalTimeStatus.LOGGEDIN).collect(Collectors.toList());
        if (filteredArrivalTimes.size() != 0)
            return filteredArrivalTimes.get(filteredArrivalTimes.size() - 1);
        return null;
    }

    public List<ArrivalTime> findAllArrivalTimesEmployee(Integer id)
    {
        return arrivalTimesRepo.getAll().stream().filter(a -> Objects.equals(a.getIdEmployee(), id)).collect(Collectors.toList());
    }

    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepo.getAll();
    }

    @Override
    public void notifyObservables() {
        this.observables.forEach(Observable::update);
    }

    public void addObservable(Observable observable) {
        this.observables.add(observable);
    }
}

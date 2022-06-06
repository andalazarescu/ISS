package service;

import javafx.util.converter.LocalDateTimeStringConverter;
import model.*;
import observer.Observable;
import observer.Observer;
import repository.IArrivalTimesRepo;
import repository.IBossRepo;
import repository.IEmployeeRepo;
import repository.IRequirementRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Service extends Observer {
    private IEmployeeRepo employeeRepo;
    private IBossRepo bossRepo;
    private IArrivalTimesRepo arrivalTimesRepo;
    private IRequirementRepo requirementRepo;

    public Service(IEmployeeRepo employeeRepo, IBossRepo bossRepo, IArrivalTimesRepo arrivalTimesRepo, IRequirementRepo requirementRepo) {
        this.employeeRepo = employeeRepo;
        this.bossRepo = bossRepo;
        this.arrivalTimesRepo = arrivalTimesRepo;
        this.requirementRepo = requirementRepo;
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
        Collection<ArrivalTime> arrivalTimes = arrivalTimesRepo.getAll();
        LocalDateTime startHour = LocalDate.now().atTime(0, 0, 0, 0);
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

    public void addRequirement(String titlu, String descriere, Weight weight, Integer id) {
        Requirement requirement = new Requirement(titlu, descriere, weight, id);
        requirementRepo.add(requirement);
    }

    @Override
    public void notifyObservables() {
        this.observables.forEach(Observable::update);
    }

    public void addObservable(Observable observable) {
        this.observables.add(observable);
    }

    public ArrayList<Requirement> getAllRequests() {
        return (ArrayList<Requirement>) requirementRepo.getAll();
    }

    public void solveRequirement(Requirement selectedRequirement) {
        selectedRequirement.setStatus(RequirementStatus.SOLVED);
        requirementRepo.update(selectedRequirement);
    }
}

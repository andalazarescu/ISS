package controller;

import observer.Observable;
import service.Service;

public class AddEmployeeWindowController implements Observable {

    private Service service;

    public void setService(Service service){
        this.service = service;
        service.addObservable(this);
    }


    @Override
    public void update() {
        System.out.println("Merge ceva");
    }
}

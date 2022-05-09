package model;

import java.time.LocalDateTime;

public class ArrivalTime implements Identifiable<Integer>{

    private Integer ID;
    private Integer idEmployee;
    private LocalDateTime time;
    private ArrivalTimeStatus status;

    public ArrivalTime(Integer idEmployee, LocalDateTime time, ArrivalTimeStatus status) {
        this.idEmployee = idEmployee;
        this.time = time;
        this.status = status;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ArrivalTimeStatus getStatus() {
        return status;
    }

    public void setStatus(ArrivalTimeStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ArrivalTime{" +
                "ID=" + ID +
                ", idEmployee=" + idEmployee +
                ", time=" + time +
                ", status=" + status +
                '}';
    }
}

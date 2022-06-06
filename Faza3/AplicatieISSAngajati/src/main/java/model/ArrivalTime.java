package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ArrivalTimes", schema = "public")
public class ArrivalTime implements Identifiable<Integer>{

    private Integer ID;
    private Integer idEmployee;
    private LocalDateTime time;
    private ArrivalTimeStatus status;

    public ArrivalTime(){};

    public ArrivalTime(Integer idEmployee, LocalDateTime time, ArrivalTimeStatus status) {
        this.idEmployee = idEmployee;
        this.time = time;
        this.status = status;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Column(name = "idEmployee")
    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Column(name = "time")
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Column(name = "status")
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

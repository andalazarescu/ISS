package model;

import java.time.LocalTime;

public class Employee implements Identifiable<Integer> {
    private Integer ID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public Employee(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer id) {
        this.ID = id;
    }
}

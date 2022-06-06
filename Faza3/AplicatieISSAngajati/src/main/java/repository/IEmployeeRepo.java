package repository;

import model.Employee;

import java.util.Collection;

public interface IEmployeeRepo extends IRepo<Employee, Integer> {
    Employee findOneByUsername(String username);
}

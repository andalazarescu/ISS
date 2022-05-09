package repository;

import model.Boss;
import model.Employee;

import java.util.Collection;

public interface IBossRepo extends IRepo<Boss, Integer> {
    Boss findOneByUsername(String username);
}

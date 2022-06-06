package repository;

import model.Employee;

import java.util.Collection;

public interface IRepo<T, Tid> {
    void add(T elem);
    void delete(T elem);
    void update(T elem);
    T findById(Tid id);
    Collection<T> getAll();
}

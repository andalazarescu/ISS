package repository;

import java.util.ArrayList;
import java.util.Collection;

public interface IRepo<T, Tid> {
    void add(T elem);
    void delete(T elem);
    void update(T elem);
    T findById(Tid id);
    ArrayList<T> getAll();
}

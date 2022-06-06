package repository;

import model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class EmployeeHibernateDBRepo implements IEmployeeRepo{

    private SessionFactory sessionFactory;

    public EmployeeHibernateDBRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Employee findOneByUsername(String username) {
        Employee employee = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                employee = session.createQuery("from Employee where username='"+username+"'", Employee.class).setMaxResults(1).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find employee by username " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return employee;
    }

    @Override
    public void add(Employee elem) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(elem);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Employee elem) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                Employee employee = findOneByUsername(elem.getUsername());
                session.delete(employee);
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la delete employee " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void update(Employee elem) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                Employee employee = findOneByUsername(elem.getUsername());
                session.update(employee);
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la update employee " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public Employee findById(Integer id) {
        Employee employee = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                employee = session.createQuery("from Employee where id='"+id+"'", Employee.class).setMaxResults(1).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find employee by id " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return employee;
    }

    @Override
    public Collection<Employee> getAll() {
        List<Employee> employees = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                employees = session.createQuery("from Employee", Employee.class).list();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find all employees " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return employees;
    }
}

package repository;

import model.Boss;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class BossHIbernateDBRepo implements IBossRepo {
    private SessionFactory sessionFactory;

    public BossHIbernateDBRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boss findOneByUsername(String username) {
        Boss boss = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                boss = session.createQuery("from Boss where username='" + username + "'", Boss.class).setMaxResults(1).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la find boss by username " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return boss;
    }

    @Override
    public void add(Boss elem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(elem);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Boss elem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Boss boss = findOneByUsername(elem.getUsername());
                session.delete(boss);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la delete boss " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void update(Boss elem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Boss boss = findOneByUsername(elem.getUsername());
                session.update(boss);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("Eroare la update employee " + e);
                if (transaction != null)
                    transaction.rollback();

            }
        }
    }

    @Override
    public Boss findById(Integer id) {
        Boss boss = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                boss = session.createQuery("from Boss where id='"+id+"'", Boss.class).setMaxResults(1).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find boss by id " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return boss;
    }

    @Override
    public Collection<Boss> getAll() {
        List<Boss> bosses = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                bosses = session.createQuery("from Boss", Boss.class).list();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find all bosses " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return bosses;
    }
}

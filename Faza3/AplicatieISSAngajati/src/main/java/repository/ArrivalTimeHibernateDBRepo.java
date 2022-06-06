package repository;

import model.ArrivalTime;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;

public class ArrivalTimeHibernateDBRepo implements IArrivalTimesRepo{
    private SessionFactory sessionFactory;

    public ArrivalTimeHibernateDBRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(ArrivalTime elem) {
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
    public void delete(ArrivalTime elem) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                ArrivalTime arrivalTime = findById(elem.getID());
                session.delete(arrivalTime);
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la delete arrivalTime " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public void update(ArrivalTime elem) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                ArrivalTime arrivalTime = findById(elem.getID());
                session.update(arrivalTime);
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la update arrivalTime " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
    }

    @Override
    public ArrivalTime findById(Integer id) {
        ArrivalTime arrivalTime = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                arrivalTime = session.createQuery("from ArrivalTime where id='"+id+"'", ArrivalTime.class).setMaxResults(1).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find arrivalTime by id " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return arrivalTime;
    }

    @Override
    public Collection<ArrivalTime> getAll() {
        List<ArrivalTime> arrivalTimes = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                arrivalTimes = session.createQuery("from ArrivalTime", ArrivalTime.class).list();
                transaction.commit();
            } catch (RuntimeException e){
                System.err.println("Eroare la find all arrivalTimes " + e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return arrivalTimes;
    }
}

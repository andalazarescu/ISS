package repository;

import model.Requirement;
import org.hibernate.SessionFactory;

import java.util.Collection;

public class RequirementHibernateRepo implements IRequirementRepo{

    private SessionFactory sessionFactory;

    public RequirementHibernateRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Requirement elem) {

    }

    @Override
    public void delete(Requirement elem) {

    }

    @Override
    public void update(Requirement elem) {

    }

    @Override
    public Requirement findById(Integer id) {
        return null;
    }

    @Override
    public Collection<Requirement> getAll() {
        return null;
    }
}

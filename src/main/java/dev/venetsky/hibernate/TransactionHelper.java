package dev.venetsky.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class TransactionHelper {

    private final SessionFactory sessionFactory;

    public TransactionHelper (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public<T> T executeInTransaction(Supplier<T> action) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();

        if(transaction.isActive()) {
            return action.get();
        }
        try {
            currentSession.beginTransaction();
            T result = action.get();
            transaction.commit();
            return result;
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            currentSession.close();
        }
    }
}

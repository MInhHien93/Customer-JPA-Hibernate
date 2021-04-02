package com.codegym.repository;

import com.codegym.models.entities.Customer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CustomerRepository implements ICustomerRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.id=:id", Customer.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Customer customer) {
        if (customer.getId() != null) {
            em.merge(customer);
        } else {
            em.persist(customer);
        }
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id);
        if (customer.getId() != null) {
            em.remove(customer);
        }

    }
}

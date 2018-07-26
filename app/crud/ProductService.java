package crud;


import entity.Smartphone;

import javax.persistence.*;
import java.util.List;

public class ProductService {

    private EntityManager em;

    @PersistenceContext(unitName="defaultPersistenceUnit")
    private EntityManagerFactory emf;

    public ProductService()
    {
        emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
        em = emf.createEntityManager();
    }
    public Smartphone add(Smartphone smartphone){
        em.getTransaction().begin();
        Smartphone smartphoneFromDB = em.merge(smartphone);
        em.getTransaction().commit();
        return smartphoneFromDB;
    }

    public void delete(long id){
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public Smartphone get(long id){
        return em.find(Smartphone.class, id);
    }

    public void update(Smartphone smartphone){
        em.getTransaction().begin();
        em.merge(smartphone);
        em.getTransaction().commit();
    }

    public List<Smartphone> getAll(){
        TypedQuery<Smartphone> namedQuery = em.createNamedQuery("Smartphone.getAll", Smartphone.class);
        return namedQuery.getResultList();
    }


}

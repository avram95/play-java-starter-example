package models;

import entity.Smartphone;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Singleton
public class JPARepository implements ProductRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPARepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Smartphone> add(Smartphone smartphone) {
        return supplyAsync(() -> wrap(em -> insert(em, smartphone)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Smartphone>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }


    @Override
    public CompletionStage<Smartphone> update(Smartphone smartphone) {
        return supplyAsync(() -> wrap(em -> {
            return update(em, smartphone);
        }), executionContext);
    }
    @Override
    public CompletionStage<Smartphone> delete(Long id) {
        return supplyAsync(() -> wrap(em -> {
            return deleteFromBD(em, id);
        }), executionContext);
    }
    @Override
    public Smartphone getItem(Long id) {
        return get(jpaApi.em(), id);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Smartphone insert(EntityManager em, Smartphone smartphone) {
        em.persist(smartphone);
        return smartphone;
    }

    private Smartphone get(EntityManager em, long id){
        return em.find(Smartphone.class, id);
    }

    private Smartphone deleteFromBD(EntityManager em, long id) {
        em.remove(get(em, id));
        System.out.println("deleteFromBD = " + id);
        return new Smartphone();
    }
    private Smartphone update(EntityManager em, Smartphone smartphone) {
        em.merge(smartphone);
        System.out.println("update = " + smartphone.toString());
        return new Smartphone();
    }
    private Stream<Smartphone> list(EntityManager em) {
        List<Smartphone> smartphones = em.createQuery("select s from Smartphone s", Smartphone.class).getResultList();
        System.out.println("smartphones list" + smartphones.toString());
        return smartphones.stream();
    }
}
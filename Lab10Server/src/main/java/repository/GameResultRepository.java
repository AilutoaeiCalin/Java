package repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entity.GameResult;

public class GameResultRepository {

    private final EntityManagerFactory emf;

    public GameResultRepository() {
        emf = Persistence.createEntityManagerFactory("quiz");
    }

    public void create(GameResult result) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(result);
        em.getTransaction().commit();

        em.close();
    }

    public void close() {
        emf.close();
    }
}
package utils;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

    private final EntityManagerFactory factory;

    public  EntityManagerProducer(){

        this.factory = Persistence.createEntityManagerFactory("Payslips");
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return this.factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager manager){
        manager.close();
    }
}

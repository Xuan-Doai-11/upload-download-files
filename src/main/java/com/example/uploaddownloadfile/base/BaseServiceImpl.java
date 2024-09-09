package com.example.uploaddownloadfile.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Slf4j
public class BaseServiceImpl {

    @Autowired
    private MultiImplService multiImplService;

    protected EntityManagerFactory getEntityManagerFactory(int fileId) {
        return multiImplService.getEntityManagerFactory(fileId);
    }

    protected EntityManager createEntityManager(int fileId) {
        EntityManagerFactory emf = getEntityManagerFactory(fileId);
        EntityManager entityManager = emf.createEntityManager();

        return entityManager;
    }


    protected<T extends JpaRepository<? , ?>> T createRepository(EntityManager entityManager, Class<T> repositoryClass) {
        T repos = new JpaRepositoryFactory(entityManager).getRepository(repositoryClass);
        return repos;
    }


    public void closeEntityManager(EntityManager entityManager) {
        if (entityManager != null) {
            try {
                boolean before = false;
                boolean after = false;

                if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                    before = true;
                }

                if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                    after = true;
                }

                if (after) {
                    entityManager.getTransaction().rollback();
                }

                entityManager.close();


            } catch (Exception e) {
                log.error("Closing EntityManager " + e.getMessage(), e);
            }
        }
    }
}

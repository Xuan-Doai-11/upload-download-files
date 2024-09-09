package com.example.uploaddownloadfile.base;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MultiImplService {

    @Autowired
    private Map<Integer, EntityManagerFactory> entityManagerFactories;

    public EntityManagerFactory getEntityManagerFactory(int fileId) {
        EntityManagerFactory emf = entityManagerFactories.get(fileId);
        return emf;
    }

}

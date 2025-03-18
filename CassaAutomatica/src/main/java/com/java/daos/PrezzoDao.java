package com.java.daos;

import com.java.entities.Prezzo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PrezzoDao extends JpaRepository<Prezzo,Integer> {
    List<Prezzo> findByArticoloId(Integer id);
}

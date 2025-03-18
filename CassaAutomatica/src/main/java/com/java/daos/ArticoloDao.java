package com.java.daos;

import com.java.entities.Articolo;
import com.java.entities.Prezzo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ArticoloDao extends JpaRepository<Articolo,Integer> {

}

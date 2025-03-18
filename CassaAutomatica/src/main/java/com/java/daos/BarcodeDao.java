package com.java.daos;

import com.java.dtos.ArticoloDto;
import com.java.entities.Articolo;
import com.java.entities.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarcodeDao extends JpaRepository<Barcode,Integer> {
    @Query(value = "SELECT a.* FROM articolo a " +
            "JOIN barcode b ON a.id = b.articolo_id " +
            "WHERE b.codice = :c limit 1", nativeQuery = true)
    Optional<Articolo> findArticoloByCodice(@Param("c") String c);
}

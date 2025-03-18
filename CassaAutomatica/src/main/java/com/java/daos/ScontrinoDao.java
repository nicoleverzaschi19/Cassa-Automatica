package com.java.daos;

import com.java.dtos.RepartoPrezzoDto;
import com.java.dtos.StockArticoloDto;
import com.java.dtos.StockIncassoArticoloDto;
import com.java.entities.Scontrino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScontrinoDao extends JpaRepository<Scontrino,Integer> {
    @Query(value = "select sum(s.totale) from scontrino s where s.data = CURRENT_DATE", nativeQuery = true)
    Optional<Double> findTotaleGiornaliero();

    @Query(value = "SELECT CAST(SUM(p.prezzo) AS DOUBLE) as sommaPrezzo,a.reparto \n" +
            "FROM articolo a \n" +
            "JOIN scontrino_articolo sb ON a.id = sb.articolo_id\n" +
            "JOIN prezzo p ON a.id = p.articolo_id\n" +
            "join scontrino s on s.id = sb.scontrino_id \n" +
            "WHERE s.data = CURRENT_DATE AND s.data BETWEEN p.data_inizio AND p.data_fine group by a.reparto", nativeQuery = true)
    List<RepartoPrezzoDto> findTotaleGiornalieroByReparto();

    @Query(value = "SELECT CAST(SUM(p.prezzo) AS DOUBLE) AS sommaPrezzo, a.reparto \n" +
            "FROM articolo a \n" +
            "JOIN scontrino_articolo sb ON a.id = sb.articolo_id\n" +
            "JOIN prezzo p ON a.id = p.articolo_id\n" +
            "JOIN scontrino s ON s.id = sb.scontrino_id \n" +
            "WHERE EXTRACT(YEAR FROM s.data) = EXTRACT(YEAR FROM CURRENT_DATE)\n" +
            "AND s.data BETWEEN p.data_inizio AND p.data_fine \n" +
            "GROUP BY a.reparto;\n", nativeQuery = true)
    List<RepartoPrezzoDto> findTotaleAnnuoByReparto();

    @Query(value = "SELECT CAST(SUM(p.prezzo) as DOUBLE) as incasso, count(a.id) as stock,a.id as articolo\n" +
            "FROM articolo a \n" +
            "JOIN scontrino_articolo sb ON a.id = sb.articolo_id\n" +
            "JOIN prezzo p ON a.id = p.articolo_id\n" +
            "join scontrino s on s.id = sb.scontrino_id \n" +
            "join stock st on st.articolo_id = a.id\n" +
            "WHERE s.data = CURRENT_DATE and st.data = CURRENT_DATE AND s.data BETWEEN p.data_inizio AND p.data_fine group by a.id\n" +
            "\n",nativeQuery = true)
    List<StockIncassoArticoloDto> findStockAndIncassoGiornaliero();

    @Query(value = "SELECT CAST((SUM(DISTINCT st.quantita) - count(a.id)) as double) as stock, a.id as articolo\n" +
            "FROM articolo a \n" +
            "JOIN scontrino_articolo sb ON a.id = sb.articolo_id\n" +
            "join scontrino s on s.id = sb.scontrino_id \n" +
            "join stock st on st.articolo_id = a.id\n" +
            "WHERE s.data = CURRENT_DATE and st.data = CURRENT_DATE group by a.id",nativeQuery = true)
    List<StockArticoloDto> findStockGiornaliero();
}

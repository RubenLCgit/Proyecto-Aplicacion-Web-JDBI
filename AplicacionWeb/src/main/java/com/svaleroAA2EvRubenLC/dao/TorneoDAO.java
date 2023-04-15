package com.svaleroAA2EvRubenLC.dao;


import com.svaleroAA2EvRubenLC.models.Torneo;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface TorneoDAO {
    @SqlQuery("SELECT * FROM TORNEO")
    @UseRowMapper(OrderMapperTorneo.class)
    List<Torneo> getTorneos();

    @SqlUpdate("INSERT INTO TORNEO(HORA_INIT, NUM_PLAZAS) VALUES (?, ?)")
    void crearTorneo (@BindBean Torneo torneo);

    @SqlUpdate("DELETE FROM JUEGOS WHERE NOMBRE= ?")
    void borrarTorneo (int id_torneo);
}

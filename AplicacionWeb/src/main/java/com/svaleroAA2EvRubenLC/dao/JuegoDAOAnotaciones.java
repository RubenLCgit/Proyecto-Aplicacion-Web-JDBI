package com.svaleroAA2EvRubenLC.dao;

import com.svaleroAA2EvRubenLC.models.Juego;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.sql.SQLException;
import java.util.List;

public interface JuegoDAOAnotaciones {
    @SqlQuery("SELECT * FROM JUEGOS")
    @UseRowMapper(OrderMapperJuego.class)
    List<Juego> getJuegos()throws SQLException;;

    @SqlUpdate("INSERT INTO JUEGOS(NOMBRE, MAX_JUGADORES, DURACION_MIN, TIPO) VALUES (?, ?, ?, ?)")
    void crearJuego (String nombre, int max_jug, int duracion_max, String tipo)throws SQLException;;

    @SqlUpdate("DELETE FROM JUEGOS WHERE NOMBRE= ?")
    void borrarJuego (String nombre)throws SQLException;;

    @SqlUpdate("UPDATE JUEGOS SET DURACION_MIN = ? WHERE NOMBRE = ?")
    void modificarJuego (int duracion, String nombre)throws SQLException;;

    @SqlQuery("SELECT * FROM JUEGOS WHERE NOMBRE= ? OR TIPO= ?")// TODO -- CON ANOTACIONES SI NO INTRODUZCO LOS DOS VALORES, NO ME MUESTRA NADA
    @UseRowMapper(OrderMapperJuego.class)
    List<Juego> buscarJuegos(String nomJue, String tipo)throws SQLException;;

    /*En el método anterior, si no introduzco dos valores validos no me muestra nada. Si uso JDBI sin anotaciones si funciona.
     En su lugar creo los dos siguientes metodos que ysaré en conjunto en la clase Menú dentro de otro metodo de esa clase*/

    @SqlQuery("SELECT * FROM JUEGOS WHERE NOMBRE= ?")//
    @UseRowMapper(OrderMapperJuego.class)
    List<Juego> buscarNomJuegos(String nomJue)throws SQLException;;;

    @SqlQuery("SELECT * FROM JUEGOS WHERE TIPO= ?")//
    @UseRowMapper(OrderMapperJuego.class)
    List<Juego> buscarTipJuegos(String tipo)throws SQLException;;

    //===========================================================================
    @SqlQuery("SELECT COUNT(*) FROM JUEGOS WHERE NOMBRE= ?")
    int existeNomJuego (String nomJue);

    @SqlQuery("SELECT COUNT(*) FROM JUEGOS WHERE TIPO= ?")
    int existeTipJuego (String tipo);
}

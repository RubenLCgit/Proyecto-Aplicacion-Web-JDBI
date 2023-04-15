package com.svaleroAA2EvRubenLC.dao;

import com.svaleroAA2EvRubenLC.models.Juego;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapperJuego implements RowMapper<Juego> {
    @Override
    public Juego map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Juego(rs.getString("NOMBRE"), rs.getInt("MAX_JUGADORES"), rs.getString("TIPO"), rs.getInt("DURACION_MIN"));
    }
}

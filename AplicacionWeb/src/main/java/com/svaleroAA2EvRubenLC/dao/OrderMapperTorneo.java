package com.svaleroAA2EvRubenLC.dao;

import com.svaleroAA2EvRubenLC.models.Torneo;
import com.svaleroAA2EvRubenLC.util.Utils;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderMapperTorneo implements RowMapper<Torneo> {
    @Override
    public Torneo map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Torneo(rs.getString("HORA_INIT"),rs.getInt("NUM_PLAZAS"));
    }
}


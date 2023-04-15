package com.svaleroAA2EvRubenLC.dao;

import com.svaleroAA2EvRubenLC.models.Club;
import com.svaleroAA2EvRubenLC.models.Juego;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.svaleroAA2EvRubenLC.util.Constants.*;

public class OrderMapperClub implements RowMapper<Club> {
    @Override
    public Club map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Club(rs.getString("ID_CLUB"),
                rs.getString("NOMBRE"),
                rs.getString("CALLE"),
                rs.getString("NUMERO"),
                rs.getString("CP"),
                rs.getString("HORARIO_APERT"),
                rs.getString("HORARIO_CIER"));
    }
}

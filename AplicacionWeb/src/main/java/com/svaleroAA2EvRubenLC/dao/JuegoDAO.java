package com.svaleroAA2EvRubenLC.dao;

import com.svaleroAA2EvRubenLC.models.Juego;
import org.jdbi.v3.core.Handle;

import java.sql.SQLException;
import java.util.List;

public class JuegoDAO {
    private Handle db; //Cualquier calse que utilice en sus metodo la conexión a la base de datos necesitará un atributo de tipo "Connection".

    public JuegoDAO(Handle db){
        this.db=db;
    }

    public void registrarJuego(Juego juego) throws SQLException { //
        String sql = "INSERT INTO JUEGOS(NOMBRE, MAX_JUGADORES, DURACION_MIN, TIPO) VALUES (?, ?, ?, ?)";
        db.createUpdate(sql)
                .bind(0, juego.getNombre())
                .bind(1,juego.getMax_jug())
                .bind(2,juego.getDuracion_max())
                .bind(3,juego.getTipo())
                .execute();
    }

    public void borrarJuego(String nombre) throws SQLException {
        String sql = "DELETE FROM JUEGOS WHERE NOMBRE= ?";
        db.createUpdate(sql)
                .bind(0,nombre)
                .execute();
    }

    public List<Juego> mostrarJuegos() throws SQLException { //TODO : LOS DATOS TIPO NUMBER LOS MUESTRA A "0"
        String sql = "SELECT * FROM JUEGOS";
        return db.createQuery(sql)
                .mapToBean(Juego.class)
                .list();
    }

    public List<Juego> filtrarJuegos(String condicion1, String condicion2) /*throws SQLException*/ {
        String sql = "SELECT * FROM JUEGOS WHERE NOMBRE = ? OR TIPO = ?";//TODO NO FUNCIONA SI METO "WHERE" COMO UN PARAMETRO "?"
        return db.createQuery(sql)
                .bind(0,condicion1)
                .bind(1, condicion2)
                .mapToBean(Juego.class)
                .list();
    }

    public boolean modificarJuego(int duracion, String nombre) throws SQLException {
        String sql = "UPDATE JUEGOS SET DURACION_MIN = ? WHERE NOMBRE = ?";
        int count = db.createUpdate(sql)
                .bind(0,duracion)
                .bind(1, nombre)
                .execute();
        return count!=0;
    }

    public boolean existeJuego(String nomJuego) throws SQLException {
        String sql = "SELECT COUNT(*) FROM JUEGOS WHERE NOMBRE= ?";
        long count = db.createQuery(sql)
                .bind(0, nomJuego)
                .mapTo(Long.class)
                .one();
        return count != 0; //SI OBTIENE LA MATRICULA BUSCADA, COUNT SERÁ IGUAL A 1 Y ENTONCES LA EXPRESION "CONUNT NO ES IGUAL A 0" SERÁ VERDADERA.
    }

    private void pasoVariablesParametros (int x, int y, String... cadenas){// TODO -- CON "..." PODEMOS PASAR CUALQUIER NÚMERO DE VARIABLES. DEBE COLOCARSE COMO UNICO PARAMETRO O SER EL ULTIMO
        for (String cadena : cadenas) {
            System.out.println(cadena);
        }
    }

    public Juego buscarJuegoNom(String nomJuego){
        String sql = "SELECT NOMBRE FROM JUEGOS WHERE NOMBRE = ?";
        Juego juego = db.createQuery(sql)
                .bind(0,nomJuego)
                .mapToBean(Juego.class)
                .one();
        return juego;
    }

    public Juego buscarJuegoID(String id){
        String sql = "SELECT NOMBRE FROM JUEGOS WHERE NOMBRE = ?";
        Juego juego = db.createQuery(sql)
                .bind(0,id)
                .mapToBean(Juego.class)
                .one();
        return juego;
    }

}

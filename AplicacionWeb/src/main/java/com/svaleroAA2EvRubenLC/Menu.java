package com.svaleroAA2EvRubenLC;

import com.svaleroAA2EvRubenLC.dao.ClubDAO;
import com.svaleroAA2EvRubenLC.dao.JuegoDAO;
import com.svaleroAA2EvRubenLC.dao.JuegoDAOAnotaciones;
import com.svaleroAA2EvRubenLC.dao.TorneoDAO;
import com.svaleroAA2EvRubenLC.models.Club;
import com.svaleroAA2EvRubenLC.models.Juego;
import com.svaleroAA2EvRubenLC.models.Torneo;
import com.svaleroAA2EvRubenLC.util.Utils;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static com.svaleroAA2EvRubenLC.util.Constants.*;

public class Menu {
    private Scanner entrada;
    private Jdbi jdbi;
    private Handle db;
    public Menu(){
        entrada = new Scanner(System.in);
        connectToDatabase();
    }

    private void connectToDatabase(){
        jdbi = Jdbi.create(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);//CREA CONEXION A LA BASE DE DATOS USANDO LA LIBRERIA JDBI
        jdbi.installPlugin(new SqlObjectPlugin());// Para dar soporte y poder utilizar las anotaciones creadas dentro del interfaz TorneoDAO.
        db = jdbi.open(); //SE CONECTA O DA ACCESO A LA BASE DE DATOS. MANEJADOR
    }

    public void mostrarMenu(){
        boolean salir = false;
        do {
            System.out.println("¿QUE QUIERES HACER?\n");
            System.out.println("1: Juegos");
            System.out.println("2: Clubs");
            System.out.println("3: Torneos");
            System.out.println("4: Jugadores");
            System.out.println("5: Salir");
            System.out.println("\n");
            System.out.print("Elige un opción: ");
            Scanner entrada = new Scanner(System.in);
            switch (Utils.comprobarEntrada(entrada.next(),1,5)){
                case "1":
                    mostrarMenuJuegos();

                    break;
                case "2":
                    mostrarMenuClubs();
                    break;
                case "3":
                    mostrarMenuTorneos();
                    break;
                case "4":
                    mostrarMenuJugador();
                    break;
                case "5":
                    salir=true;
                    db.close();
                    break;
                default:
                    System.out.println("Opción no valida. Vuelve a intentarlo:");
            }
        }while(!salir);
    }

    private void mostrarMenuJuegos(){
        boolean salir = false;

        do {
            System.out.println("JUEGOS DE MESA\n");
            System.out.println("1: Registrar Juego");
            System.out.println("2: Modificar Juego");
            System.out.println("3: Buscar Juego");
            System.out.println("4: Eliminar Juego");
            System.out.println("5: Mostrar todos los Juegos");
            System.out.println("6: Salir");
            System.out.println("\n");
            System.out.print("Elige un opción: ");
            Scanner entrada = new Scanner(System.in);
            switch (Utils.comprobarEntrada(entrada.next(),1,5)){

                case "1":
                    menuRegistrarJuego2();
                    break;
                case "2":
                    menuModificarJuego2();
                    break;
                case "3":
                    menuBuscarJuego2();
                    break;
                case "4":
                    menuBorrarJuego2();
                    break;
                case "5":
                    menuMostrarJuegos2();//TODO : LOS DATOS TIPO NUMBER LOS MUESTRA A "0"
                    break;
                case "6":
                    salir=true;
                    db.close();
                    break;
                default:
                    System.out.println("Opción no valida. Vuelve a intentarlo:");
            }

        }while(!salir);

    }

    private void menuRegistrarJuego(){
        System.out.print("Nombre del juego: ");
        String nombre = entrada.nextLine();
        // TODO COMPROBAR SI YA EXISTE UN JUEGO CON ESE NOMBRE
        System.out.print("Número máximo de jugadores: ");
        int max_jug = Integer.parseInt(entrada.nextLine());
        System.out.print("Tipo de juego: ");
        String tipo = entrada.nextLine();
        System.out.print("Duración máxima del juego (minutos): ");
        int duracion_max = Integer.parseInt(entrada.nextLine());
        Juego juego = new Juego(nombre, max_jug,tipo,duracion_max);
        JuegoDAO juegoDAO = new JuegoDAO(db);
        try {
            juegoDAO.registrarJuego(juego);//Capturamos aqui la excepción lanzada por el método registrarJuego. También podríamos volver a lanzarla a otra clase superior donde se use el metodo menuRegistrarJuego y asi sucesivamente hasta que la capturemos.
            System.out.println("Juego registrado correctamente");
        }catch (SQLException sqle){
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuRegistrarJuego2(){
        System.out.print("Nombre del juego: ");
        String nombre = entrada.nextLine();
        System.out.print("Número máximo de jugadores: ");
        int max_jug = Integer.parseInt(entrada.nextLine());
        System.out.print("Tipo de juego: ");
        String tipo = entrada.nextLine();
        System.out.print("Duración máxima del juego (minutos): ");
        int duracion_max = Integer.parseInt(entrada.nextLine());
        try{
            if (jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.existeNomJuego(nombre)==0)){
                jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> {
                    dao.crearJuego(nombre,max_jug,duracion_max,tipo);
                    return null;
                });
                System.out.println("El Juego se ha registrado correctamente");
            }else System.out.println("El juego ya existe");
        }catch (SQLException sqle) {
             System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuBorrarJuego(){
        System.out.print("Nombre del juego a borrar: ");
        String nombre = entrada.nextLine();
        JuegoDAO juegoDAO = new JuegoDAO(db);
        try {//Capturamos aqui la excepcion lanzada por el metodo registrarJuego. Tambien podriamos volver a lanzarla a otra clase superior donde se use el metodo menuRegistrarJuego y asi sucesivamente hasta que la capturemos.
            if (juegoDAO.existeJuego(nombre)){
                juegoDAO.borrarJuego(nombre);
                System.out.println("Juego borrado correctamente");
            }else System.out.println("Juego no encontrado");
        }catch (SQLException sqle){
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuBorrarJuego2(){
        System.out.print("Nombre del juego a borrar: ");
        String nombre = entrada.nextLine();
        try{
            if (jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.existeNomJuego(nombre)!=0)){
                jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> {
                    dao.borrarJuego(nombre);
                    return null;
                });
                System.out.println("El Juego se ha borrado correctamente");
            }else System.out.println("El juego no esta registrado");
        }catch (SQLException sqle) {
            System.out.println("Fallo de Conexión con la base de datos");
        }

    }

    private void menuMostrarJuegos(){//TODO : LOS DATOS TIPO NUMBER LOS MUESTRA A "0"
        JuegoDAO juegoDAO = new JuegoDAO(db);
        try{
            if (!juegoDAO.mostrarJuegos().isEmpty())
                for (int i=0;i<juegoDAO.mostrarJuegos().size();i++){
                    System.out.println(juegoDAO.mostrarJuegos().get(i));
                }
            else System.out.println("No hay datos registrados");
        }catch (SQLException sqle){
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuMostrarJuegos2(){//Metodo para mostrar lista de la tabla juegos pero con anotaciones e interfaz
        try{
            List<Juego> juegos = jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.getJuegos());
            juegos.forEach(System.out::println);
        }catch (SQLException sqle) {
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuBuscarJuego(){
        JuegoDAO juegoDAO = new JuegoDAO(db);
        System.out.print("Nombre del juego que quieres buscar (0 para ningún valor): ");
        String condicion1 = entrada.nextLine();
        System.out.print("Y/O Tipo de juego a buscar (0 para ningún valor): ");
        String condicion2 = entrada.nextLine();
        if (!juegoDAO.filtrarJuegos(condicion1,condicion2).isEmpty())
            for (int i=0;i<juegoDAO.filtrarJuegos(condicion1,condicion2).size();i++){
                System.out.println(juegoDAO.filtrarJuegos(condicion1,condicion2).get(i));
            }
        else System.out.println("Juegos no encontrados");
    }

    private void menuBuscarJuego2(){
        System.out.print("Nombre del juego que quieres buscar (0 para ningún valor): ");
        String nombre = entrada.nextLine();
        System.out.print("Y/O Tipo de juego a buscar (0 para ningún valor): ");
        String tipo = entrada.nextLine();
        try{
            if (jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.existeTipJuego(tipo)!=0)){
                List<Juego> juegos = jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.buscarTipJuegos(tipo));
                juegos.forEach(System.out::println);}
            if (jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.existeNomJuego(nombre)!=0)){
                List<Juego> juegos = jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.buscarNomJuegos(nombre));
                juegos.forEach(System.out::println);}
            if (jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.existeTipJuego(tipo)==0)&&jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.existeNomJuego(nombre)==0))
                System.out.println("El/los juego/s no esta/n registrado/s en la base de datos");
        }catch (SQLException sqle) {
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuModificarJuego() {
        JuegoDAO juegoDAO = new JuegoDAO(db);
        System.out.print("Nombre del juego que quieres modificar: ");
        String nombre = entrada.nextLine();
        System.out.print("Especifica la duración maxima del juego: ");
        int duracion = Integer.parseInt(entrada.nextLine());
        try {
            if (juegoDAO.modificarJuego(duracion, nombre)){
                juegoDAO.modificarJuego(duracion, nombre);
                System.out.println("Juego modificado");
            }else System.out.println("Juego no encontrado");

        }catch (SQLException sqle) {
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuModificarJuego2() {
        System.out.print("Nombre del juego que quieres modificar: ");
        String nombre = entrada.nextLine();
        System.out.print("Especifica la duración maxima del juego: ");
        int duracion = Integer.parseInt(entrada.nextLine());
        try{
            if (jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> dao.existeNomJuego(nombre)!=0)){
                jdbi.withExtension(JuegoDAOAnotaciones.class, dao -> {
                    dao.modificarJuego(duracion, nombre);
                    System.out.println("Duración del juego modificada");
                    return null;
                });
            }else System.out.println("El juego no esta registrado en la base de datos");
        }catch (SQLException sqle) {
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void mostrarMenuClubs(){
        boolean salir = false;
        do {
            System.out.println("CLUBS\n");
            System.out.println("\n");
            System.out.println("1: Registrar Club");
            System.out.println("2: Modificar Club");
            System.out.println("3: Buscar Club");
            System.out.println("4: Eliminar Club");
            System.out.println("5: Mostrar todos los Clubs");
            System.out.println("6: Salir");
            System.out.println("\n");
            System.out.print("Elige un opción: ");
            Scanner entrada = new Scanner(System.in);
            switch (Utils.comprobarEntrada(entrada.next(),1,5)){
                case "1":
                    menuRegistrarClub();
                    break;
                case "2":
                    menuModificarClub();
                    break;
                case "3":
                    menuBuscarClub();
                    break;
                case "4":
                    menuBorrarClub();
                    break;
                case "5":
                    menuMostrarClubs();
                    break;
                case "6":
                    salir=true;
                    db.close();
                    break;
                default:
                    System.out.println("Opción no valida. Vuelve a intentarlo:");
            }
        }while(!salir);
    }

    private void menuRegistrarClub(){
        System.out.print("Nombre del Club: ");
        String nomClub = entrada.nextLine();
        System.out.println("Dirección del Club: \n");
        System.out.print("    Calle: ");
        String nomCalle =  entrada.nextLine();
        System.out.print("    Numero: ");
        String numCalle = entrada.nextLine();
        System.out.print("Código Postal: ");
        String cp = entrada.nextLine();
        System.out.print("Horario de Apertura (HH:MM): ");
        String hor_apert = entrada.nextLine();
        System.out.print("Horario de Cierre (HH:MM): ");
        String hor_cier = entrada.nextLine();
        try{
            if (jdbi.withExtension(ClubDAO.class, dao -> dao.existeNomClub(nomClub)==0)){
                jdbi.withExtension(ClubDAO.class, dao -> {
                    dao.crearClub(nomClub,nomCalle,numCalle, cp, hor_apert, hor_cier);
                    return null;
                });
                System.out.println("El Club se ha registrado");
            }else System.out.println("El Club ya está registrado en la base de datos");
        }catch (SQLException sqle) {
        if (sqle.getErrorCode() == 2290){
            System.out.println("Error: El horario debe estar en el siguiente formato 'HH:MM'");
        }else System.out.println("Fallo de Conexión con la base de datos");
    }
    }

    private void menuModificarClub() {
        System.out.print("Nombre del Club que quieres modificar: ");
        String nomClub = entrada.nextLine();
        System.out.print("Especifica el nuevo horario de apertura para el Club (HH:MM): ");
        String horario_apert = entrada.nextLine();
        try {
            if (jdbi.withExtension(ClubDAO.class, dao -> dao.existeNomClub(nomClub) != 0)) {
                jdbi.withExtension(ClubDAO.class, dao -> {
                    dao.modificarClub(horario_apert, nomClub);
                    return null;
                });
                System.out.println("Horario de apertura del Club modificado");
            } else System.out.println("El Club no está registrado en la base de datos");
        }catch (SQLException sqle) {
            if (sqle.getErrorCode() == 2290){
                System.out.println("Error: El horario debe estar en el siguiente formato 'HH:MM'");
            }else System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuBuscarClub() {
        System.out.print("Nombre del juego que quieres buscar (0 para ningún valor): ");
        String nomClub = entrada.nextLine();
        System.out.print("Y/O Codigo Postal de el/los Club/s a buscar (0 para ningún valor): ");
        String cp = entrada.nextLine();
        try {
            if (jdbi.withExtension(ClubDAO.class, dao -> dao.existeCpClub(cp) != 0)) {
                List<Club> clubs = jdbi.withExtension(ClubDAO.class, dao -> dao.buscarCpClub(cp));
                clubs.forEach(System.out::println);}
            if (jdbi.withExtension(ClubDAO.class, dao -> dao.existeNomClub(nomClub) != 0)) {
                List<Club> clubs = jdbi.withExtension(ClubDAO.class, dao -> dao.buscarNomClub(nomClub));
                clubs.forEach(System.out::println);}
            if (jdbi.withExtension(ClubDAO.class, dao -> dao.existeCpClub(cp) == 0)&&jdbi.withExtension(ClubDAO.class, dao -> dao.existeNomClub(nomClub) == 0))
                System.out.println("El/los juego/s no esta/n registrado/s en la base de datos");
        } catch (SQLException sqle) {
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void menuMostrarClubs(){
        try{
            List<Club> clubs = jdbi.withExtension(ClubDAO.class, dao -> dao.getClub());
            clubs.forEach(System.out::println);
        }catch (SQLException sqle) {
            System.out.println("Fallo de Conexión con la base de datos");
        }

    }

    private void menuBorrarClub(){
        System.out.print("Nombre del Club a borrar: ");
        String nomClub = entrada.nextLine();
        try {
            if (jdbi.withExtension(ClubDAO.class, dao -> dao.existeNomClub(nomClub) != 0)) {
                jdbi.withExtension(ClubDAO.class, dao -> {
                    dao.borrarClub(nomClub);
                    return null;
                });
                System.out.println("Datos sobre el Club borrados");
            } else System.out.println("El Club no está registrado en la base de datos");
        }catch (SQLException sqle) {
            System.out.println("Fallo de Conexión con la base de datos");
        }
    }

    private void mostrarMenuTorneos(){
        boolean salir = false;
        do {
            System.out.println("TORNEOS\n");
            System.out.println("\n");
            System.out.println("1: Registrar Torneo");
            System.out.println("2: Modificar Torneo");
            System.out.println("3: Buscar Torneo");
            System.out.println("4: Eliminar Torneo");
            System.out.println("5: Mostrar todos los Torneos");
            System.out.println("6: Salir");
            System.out.println("\n");
            System.out.print("Elige un opción: ");
            Scanner entrada = new Scanner(System.in);
            switch (Utils.comprobarEntrada(entrada.next(),1,5)){
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    menuMostrarTorneo();
                    break;
                case "6":
                    salir=true;
                    db.close();
                    break;
                default:
                    System.out.println("Opción no valida. Vuelve a intentarlo:");
            }
        }while(!salir);
    }

    private void menuMostrarTorneo(){//Metodo para mostrar lista de la tabla juegos pero con anotaciones e interfaz
        List<Torneo> torneos = jdbi.withExtension(TorneoDAO.class, dao -> dao.getTorneos());
        torneos.forEach(System.out::println);
    }


    private void menuRegistrarTorneo(){
        System.out.print("Hora de inicio del Torneo: ");
        String hora_init = entrada.nextLine();
        // TODO COMPROBAR SI YA EXISTE UN TORNEO A ESA HORA EN EL MISMO CLUB
        System.out.print("Máximo de plazas disponible: ");
        int max_plaz = Integer.parseInt(entrada.nextLine());
        Torneo torneo = new Torneo(hora_init,max_plaz);
    }

    private void mostrarMenuJugador(){
        boolean salir = false;
        do {
            System.out.println("JUGADORES\n");
            System.out.println("\n");
            System.out.println("1: Registrar Jugador");
            System.out.println("2: Modificar Jugador");
            System.out.println("3: Buscar Jugador");
            System.out.println("4: Eliminar Jugador");
            System.out.println("5: Mostrar todos los Jugadores");
            System.out.println("6: Salir");
            System.out.println("\n");
            System.out.print("Elige un opción: ");
            Scanner entrada = new Scanner(System.in);
            switch (Utils.comprobarEntrada(entrada.next(),1,5)){
                case "1":

                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "4":

                    break;
                case "5":

                    break;
                case "6":
                    salir=true;
                    db.close();
                    break;
                default:
                    System.out.println("Opción no valida. Vuelve a intentarlo:");
            }
        }while(!salir);
    }

}

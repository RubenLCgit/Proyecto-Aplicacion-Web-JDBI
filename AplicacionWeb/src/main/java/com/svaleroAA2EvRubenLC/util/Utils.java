package com.svaleroAA2EvRubenLC.util;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class Utils {
    public static String comprobarEntrada (String num_intro, int nuMenor, int nuMayor){
        Scanner entrada= new Scanner(System.in);
        /*int num=Integer.parseInt(num_intro);*/
        while (!num_intro.matches("[0-9]*")/*||num==0||num>nuMayor*/){
            System.out.println("No es una opción valida.\n");
            System.out.print("Las opciones validas solo comprender valores entre el 1 y el "+nuMayor+": \n");
            num_intro=entrada.nextLine();
            /*num=Integer.parseInt(num_intro);*/
        }
        return num_intro;
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}

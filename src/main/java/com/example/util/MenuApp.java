package com.example.util;

import com.example.service.ConversorMoneda;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class MenuApp {
    public static void mostrarMenuPrincipal(){
        System.out.println("""
            +-------------------------------------------------------------------------------+
            |                     CONVERSOR DE MONEDAS - EXCHANGERATE                       |
            +-------------------------------------------------------------------------------+
            Bienvenid@, te ofrecemos los precios más actualizados
            ¿Qué deseas hacer? Ingresa el dígito de la opción elegida
            [1] Ver el precio del Dólar
            [2] Convertir Dólar (USD) <--> Peso Argentino (ARS)
            [3] Convertir Dólar (USD) <--> Boliviano (BOB)
            [4] Convertir Dólar (USD) <--> Real Brasileño (BRL)
            [5] Convertir Dólar (USD) <--> Peso Chileno (CLP)
            [6] Convertir Dólar (USD) <--> Peso Colombiano (COP)
            [7] Convertir Dólar (USD) <--> Sol Peruano (PEN)
            
            [0] Salir de la aplicación
            
            """
        );
    }

    public static void limpiarPantalla(){
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }

    public static int seleccionarOpcion(int maxOpcion) {
        Scanner in = new Scanner(System.in);
        int opcion = -1;
        while (true) {
            try {
                System.out.print("Ingresa una opción válida: ");
                opcion = in.nextInt();
                in.nextLine();
                if (opcion >= 0 && opcion <= maxOpcion) {
                    return opcion;
                } else {
                    System.out.println("La opción debe estar entre 0 y " + maxOpcion + ".");
                }
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Opción no válida, prueba con una de las opciones mostradas :)");
            }
        }
    }

    public static void mostrarTipoCambio(Map<String, Double> tasas) {
        Scanner in = new Scanner(System.in);
        LocalDate fechaActual = LocalDate.now();
        System.out.printf("""
            
            Para la fecha de hoy %s , 1 Dólar (USD) equivale a:
            >> %.2f Pesos Argentinos (ARS)
            >> %.2f Bolivianos (BOB)
            >> %.2f Reales Brasileños (BRL)
            >> %.2f Pesos Chilenos (PCL)
            >> %.2f Pesos Colombianos (COP)
            >> %.2f Soles Peruanos (PEN)
            
            """,
        fechaActual,
        tasas.get("ARS"), tasas.get("BOB"), tasas.get("BRL"),tasas.get("CLP"),tasas.get("COP"),tasas.get("PEN")
        );
        solicitarContinuar();
    }

    public static void ejecutarConversion(int opcion, ConversorMoneda conv){
        String[] monedas = {"ARS", "BOB", "BRL", "CLP", "COP", "PEN"};
        Scanner in = new Scanner(System.in);
        double monto = -1.0;

        System.out.printf("""
            +-------------------------------------------------------------------------------+
            |                     CONVERSOR DE MONEDAS - EXCHANGERATE                       |
            +-------------------------------------------------------------------------------+
            Realizando conversion USD <--> %s
            """,monedas[opcion-2]
        );
        while (true) {
            try {
                System.out.println("Ingresa el monto a convertir: ");
                monto = in.nextDouble();
                in.nextLine();
                if (monto > 0) {
                    System.out.printf("[0] Convertir %s a USD      [1] Convertir USD a %s\n",
                        monedas[opcion-2], monedas[opcion-2]);
                    if (seleccionarOpcion(1) == 0){
                        System.out.printf("El valor %.2f [%s] equivale a %.2f [USD]\n",
                            monto,
                            monedas[opcion-2],
                            conv.convertirMoneda(monedas[opcion-2],"USD", monto)
                        );
                    }
                    else {
                        System.out.printf("El valor %.2f [USD] equivale a %.2f [%s]\n",
                            monto,
                            conv.convertirMoneda("USD",monedas[opcion-2], monto),
                            monedas[opcion-2]
                        );
                    }
                    break;
                } else {
                    System.out.println("El monto ingresado debe ser mayor a 0");
                }
            } catch (Exception e) {
                in.nextLine();
                System.out.println("Ingreso no válido, intenta con un número válido");
            }
        }
        solicitarContinuar();
    }

    public static void solicitarContinuar(){
        System.out.print("Presiona cualquier tecla para continuar ... ");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Error al esperar entrada del usuario.");
        }
    }

    public static void salir(){
        System.out.println("Gracias por usar la aplicación. ¡Hasta pronto!");
        System.exit(0);
    }
}

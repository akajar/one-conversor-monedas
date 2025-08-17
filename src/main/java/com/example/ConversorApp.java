package com.example;

import com.example.service.ConversorMoneda;
import com.example.util.MenuApp;

public class ConversorApp {
    public static void main(String[] args) {
        ConversorMoneda app = new ConversorMoneda();
        int opcion = -1;
        while(true){
            //MenuApp.limpiarPantalla();
            MenuApp.mostrarMenuPrincipal();
            opcion = MenuApp.seleccionarOpcion(7);
            switch (opcion){
                case 0: MenuApp.salir(); break;
                case 1: MenuApp.mostrarTipoCambio(app.getTasasCambio()); break;
                default: MenuApp.ejecutarConversion(opcion, app); break;
            }
        }
    }
}

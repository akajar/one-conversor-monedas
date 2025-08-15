package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracion {
    private static final Properties propiedades = new Properties();

    static {
        try (InputStream input = Configuracion.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo config.properties");
            }
            propiedades.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar config.properties", e);
        }
    }

    public static String obtener(String clave) {
        return propiedades.getProperty(clave);
    }
}

package com.example.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class ClienteAPI {
    protected String baseUrl;
    protected String token;

    public abstract HttpRequest crearSolicitud(String endpoint);

    public String obtenerRespuesta(HttpRequest solicitud){
        try{
            HttpResponse<String> respuesta = HttpClient
                    .newHttpClient()
                    .send(solicitud, HttpResponse.BodyHandlers.ofString());
            if (respuesta.statusCode() != 200) {
                throw new RuntimeException("Error en la solicitud: " + respuesta.statusCode());
            }
            return respuesta.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al ejecutar la solicitud", e);
        }
    }
}

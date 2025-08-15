package com.example.service;

import com.example.util.Configuracion;

import java.net.URI;
import java.net.http.HttpRequest;

public class ExchangeRateAPI extends ClienteAPI{
    public ExchangeRateAPI(){
        this.baseUrl = Configuracion.obtener("exchange_rate.base.url");
        this.token = Configuracion.obtener("exchange_rate.token");
    }

    @Override
    public HttpRequest crearSolicitud(String endpoint) {
        URI direccion = URI.create(baseUrl + endpoint);
        return HttpRequest.newBuilder()
                .uri(direccion)
                .header("Authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }
}

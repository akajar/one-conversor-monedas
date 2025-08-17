package com.example.service;

import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConversorMoneda {
    private final Map<String, Double> tasasCambio; //cambia solo al cce

    public ConversorMoneda() {
        //se almacenan las tasas directamente para evitar el consumo completo de la cuota.
        this.tasasCambio = obtenerTasas(Arrays.asList("ARS", "BOB", "BRL", "CLP", "COP", "PEN"));
    }

    private ClienteAPI iniciarCliente(){//abierto a personalizacion con otras APIs
        return new ExchangeRateAPI();
    }

    private Map<String, Double> obtenerTasas(List<String> codMonedas){
        ClienteAPI cliente = iniciarCliente();
        return codMonedas.stream().collect(Collectors.toMap(
        codMoneda -> codMoneda , //key
        codMoneda -> { //value
            //se obtienen las tasas con el precio de USD en las monedas de la lista
            String jsonBody = cliente.obtenerRespuesta(cliente.crearSolicitud("/pair/USD/" + codMoneda));
            return JsonParser.parseString(jsonBody)
                    .getAsJsonObject()
                    .get("conversion_rate")
                    .getAsDouble();
        }));
    }

    public Double convertirMoneda(String monedaOrigen, String monedaDestino, Double monto){
        //buscar moneda origen en la lista de claves
        if (this.tasasCambio.containsKey(monedaOrigen)){
            //si aparece es porque se cambia la moneda origen a dolares (por ahora)
            return monto / this.tasasCambio.get(monedaOrigen);
        }
        //si no aparece es porque estan cambiando dolares hacia la moneda destino (por ahora)
        return monto * this.tasasCambio.get(monedaDestino);
    }
}

package com.wgeneric.microservices.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.wgeneric.microservices.models.entidades.Interfaces;

public class HttpClientRest {

    public Map<String,String> ClientConnectPost(String solitudEntidad, Map<String, String> headers, Interfaces interfaz) {
        String responseBody = null;


        Map<String, String> respuestaHttp = new HashMap<>();

 


        // Crear un cliente HTTP con un tiempo de espera de 10 segundos
        HttpClient httpClient = HttpClient.newBuilder()
                                          .connectTimeout(java.time.Duration.ofSeconds(interfaz.getTimeout()))
                                          .build();


        // Construir la solicitud HTTP GET/POST con cabeceras
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(interfaz.getEndpoint()));

        // Agregar las cabeceras al builder de la solicitud
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder = requestBuilder.header(entry.getKey(), entry.getValue());
        }

        // Construir la solicitud HTTP
        HttpRequest request = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(solitudEntidad))
                .build();

        // Enviar la solicitud HTTP POST y capturar la respuesta
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Obtener el cuerpo de la respuesta JSON como una cadena
            responseBody = response.body();
             System.out.println("Cuerpo de la respuesta: " + responseBody);
             respuestaHttp.put("codigoRespuesta", String.valueOf(response.statusCode()));  
             respuestaHttp.put("body", responseBody);  
          } catch (SocketTimeoutException e) {
            //TODO: Manejar cuando el tiempo de espera se acaba para la entidad
            System.err.println("La solicitud ha excedido el tiempo de espera.");
            e.printStackTrace();

        } catch (Exception e) {
            // TODO: Controlar erro de respuesta de la entidad metodo post 
            e.printStackTrace();
        }
        return respuestaHttp;

    }

    public Map<String,String> ClientConnectGet(String solitudEntidad, Map<String, String> headers, Interfaces interfaz) throws IOException, InterruptedException {
        String responseBody = null;

        HttpClient httpClient = HttpClient.newHttpClient();
        Map<String, String> respuestaHttp = new HashMap<>();


// Build the HttpRequest with headers
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(interfaz.getEndpoint()))
                .GET();
        headers.forEach(requestBuilder::header);
        HttpRequest request = requestBuilder.build();



        // Enviar la solicitud HTTP POST y capturar la respuesta
        try {
            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the status code and response body
            System.out.println("Status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());


             respuestaHttp.put("codigoRespuesta", String.valueOf(response.statusCode()));  
             respuestaHttp.put("body", response.body());
          } catch (SocketTimeoutException e) {
            //TODO: Manejar cuando el tiempo de espera se acaba para la entidad
            System.err.println("La solicitud ha excedido el tiempo de espera.");
            e.printStackTrace();

        } catch (Exception e) {
            // TODO: Controlar erro de respuesta de la entidad metodo post 
            e.printStackTrace();
        }
        return respuestaHttp;

    }


}

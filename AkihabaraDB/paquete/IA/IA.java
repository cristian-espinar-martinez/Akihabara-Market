package paquete.IA;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class IA {

    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions"; // URL de la API
    private static final String MODEL = "mistralai/mistral-7b-instruct:free"; // Modelo a usar
    private static String API_KEY = ""; // Aquí se guarda la clave API

    private final HttpClient httpClient; // Cliente HTTP para hacer peticiones
    private final Gson gson; // Para convertir objetos a JSON y viceversa

    public IA() {
        httpClient = HttpClient.newHttpClient(); // Crear cliente HTTP
        gson = new Gson(); // Crear objeto Gson
        cargarAPIKeyDesdeConfig(); // Cargar API key desde archivo config.properties
    }

    private void cargarAPIKeyDesdeConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis); // Leer archivo config.properties
            API_KEY = props.getProperty("api.key"); // Obtener la clave API

            if (API_KEY == null || API_KEY.isEmpty()) {
                System.err.println("'api.key' no encontrada en config.properties."); // Avisar si falta clave
            }
        } catch (IOException e) {
            System.err.println(" Error al cargar config.properties:");
            e.printStackTrace(); // Mostrar error si no se puede leer archivo
        }
    }

    public String obtenerRespuesta(String promptUsuario) {
        if (API_KEY == null || API_KEY.isEmpty()) {
            // Avisar si no hay clave API antes de hacer la petición
            return " Error: API key no encontrada. Asegúrate de tener un archivo 'config.properties' con 'api.key=TU_CLAVE'.";
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", MODEL); // Modelo usado en la petición

        // Crear lista de mensajes (en este caso solo el mensaje del usuario)
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", promptUsuario));
        requestBody.put("messages", messages);

        String jsonRequest = gson.toJson(requestBody); // Convertir el cuerpo a JSON

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL)) // URL de la API
                .header("Authorization", "Bearer " + API_KEY) // Autorizar con clave
                .header("Content-Type", "application/json") // Tipo JSON
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest)) // Enviar petición POST
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                // Si hay error en la respuesta, devolver mensaje con código y cuerpo
                return " Error en la API: Código " + response.statusCode() + " - " + response.body();
            }

            JsonObject json = gson.fromJson(response.body(), JsonObject.class); // Parsear respuesta JSON
            JsonArray choices = json.getAsJsonArray("choices"); // Obtener respuestas

            if (choices != null && choices.size() > 0) {
                JsonObject mensaje = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                return mensaje.get("content").getAsString().trim(); // Devolver texto generado
            } else {
                return " No se recibió respuesta válida del modelo."; // No hubo respuestas
            }

        } catch (Exception e) {
            // Error al conectar o procesar la respuesta
            return " Error al conectar con OpenRouter: " + e.getMessage();
        }
    }
}

package unoeste.fipp.sistemafx.db.viacep;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaCEP {
    public static EnderecoRecordAPI consulta(String cep) {
        URI address = URI.create("https://viacep.com.br/ws/" + cep + "/json/");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(address)
                .build();

        try {
            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Parseando a string JSON diretamente como um objeto JsonObject
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            // Capturando os campos do JSON
            String cepStr = jsonObject.get("cep").getAsString();
            String logradouro = jsonObject.get("logradouro").getAsString();
            String bairro = jsonObject.get("bairro").getAsString();
            String localidade = jsonObject.get("localidade").getAsString();
            String uf = jsonObject.get("uf").getAsString();

            // Retornando o Endereço diretamente
            return new EnderecoRecordAPI(cepStr, logradouro, bairro, localidade, uf);

        } catch (Exception e) {
            throw new RuntimeException("CEP inválido ou erro na consulta");
        }
    }
}

package com.marketplace.services;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matheus Alves
 * Service used to retrieve the metadata related to the current GCP VM instance.
 * For futher more details about this approach, read this article: https://cloud.google.com/compute/docs/metadata/querying-metadata
 */
@Service
public class GCPMetadataFetcherService {

    private static final String METADATA_SERVER = "http://metadata.google.internal/computeMetadata/v1/instance/";

    public Map<String, String> getInstanceMetadata() {
        Map<String, String> result = new HashMap<>();

        HttpClient client = HttpClient.newHttpClient();
        List<String> endpoints = List.of(
                "name",
                "id",
                "zone",
                "machine-type",
                "network-interfaces/0/access-configs/0/external-ip",
                "network-interfaces/0/ip"
        );

        for (String endpoint : endpoints) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(METADATA_SERVER + endpoint))
                        .header("Metadata-Flavor", "Google")
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(
                        request, HttpResponse.BodyHandlers.ofString()
                );

                result.put(endpoint, response.body());

            } catch (Exception e) {
                System.out.printf("Could retrieve (%s) metadata from VM: %s%n", endpoint, e.getCause());
            }
        }
        return result;
    }

}
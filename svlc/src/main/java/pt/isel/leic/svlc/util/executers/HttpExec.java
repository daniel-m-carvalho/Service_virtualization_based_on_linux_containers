package pt.isel.leic.svlc.util.executers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;

/**
 * Provides utility methods for making HTTP requests.
 * This class is generic and can be used for any HTTP request.
 */
public class HttpExec {

    public static final String defaultPayload = ""; // Default payload for requests
    private static final String contentType = "application/json"; // Default content type for requests
    private static final Map<String, ReqBuilder> clientBuilderMap = Map.of(
            "GET", (builder, body) -> builder.GET(),
            "POST", HttpRequest.Builder::POST,
            "PUT", HttpRequest.Builder::PUT,
            "DELETE", (builder, body) -> builder.DELETE()
    );

    /**
     * Executes an HTTP request with the specified parameters.
     *
     * @param method The HTTP method to use (GET, POST, PUT, DELETE).
     * @param endpoint The URL endpoint to send the request to.
     * @param payload The request payload as a String. For GET requests, this should be null or empty.
     * @param queryParams A map of query parameters to append to the endpoint URL.
     *                    Can be null if there are no parameters.
     * @param authInfo Base-64 encoded authentication information to be included in the request header.
     *                 Can be null if not required.
     * @return The response body as a String.
     * @throws Exception if the request fails or an unsupported method is specified.
     */
    public static Map<String, Object> executeRequest(String method, String endpoint, String payload, Map<String, String> queryParams, String authInfo) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Construct the query string from queryParams
        String queryString = queryParams != null ? queryParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((param1, param2) -> param1 + "&" + param2)
                .map(params -> "?" + params)
                .orElse("") : "";

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(new URI(endpoint + queryString))
                .header("Content-Type", contentType); // Set content type header

        // If authInfo is provided, add it as the X-Registry-Auth header
        if (authInfo != null && !authInfo.isEmpty()) {
            requestBuilder.header("X-Registry-Auth", authInfo);
        }

        HttpRequest request = clientBuilderMap.getOrDefault(
                method,
                (builder, body) -> {
                    throw new Exception("Unsupported method: " + method);
                }
        ).apply(
                requestBuilder,
                BodyPublishers.ofString(payload)
        ).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ExecIfElse.execIf(
            isError(response.statusCode()),
            () -> {
                throw new Exception("Request failed with status code: " + response.statusCode() + "\n" + response.body());
            }
        );

        return Map.of("status", response.statusCode(), "body", response.body());
    }

    /**
     * Determines if the given status code represents an error.
     *
     * @param statusCode The HTTP status code to check.
     * @return true if the status code is in the range of 400-599, indicating an error; false otherwise.
     */
    private static boolean isError(int statusCode) {
        return statusCode >= 400 && statusCode < 600;
    }
}
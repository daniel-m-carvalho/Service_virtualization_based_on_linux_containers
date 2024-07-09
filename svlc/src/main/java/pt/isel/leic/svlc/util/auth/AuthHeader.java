package pt.isel.leic.svlc.util.auth;

import java.util.Base64;

public class AuthHeader {
    public static String createAuthHeader() {
        //get username and access token to environment variables
        String username = System.getenv("USERNAME_ENV_VAR");
        String token = System.getenv("TOKEN_ENV_VAR");
        // Manually constructing the JSON string
        String authJson = "{\"username\":\"" + username + "\", \"password\":\"" + token + "\"}";
        // Encoding the JSON string using Base64
        return Base64.getEncoder().encodeToString(authJson.getBytes());
    }
}
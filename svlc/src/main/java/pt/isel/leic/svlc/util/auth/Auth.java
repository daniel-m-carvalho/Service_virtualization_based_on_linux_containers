package pt.isel.leic.svlc.util.auth;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Auth {

    public static String createAuthHeader() {
        String format = "{\"username\":\"%s\",\"token\":\"%s\"}";
        return encode(format, false);
    }

    public static byte[] registryKey() {
        String dockerConfigJson = "{\"auths\":{\"hub.docker.com\":{\"username\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"auth\":\"%s\"}}}";
        return encode(dockerConfigJson, true).getBytes();
    }

    public static String getKubernetesToken(String cluster) throws Exception {
        String keyPath = System.getProperty("user.home") + "\\.minikube\\profiles\\" + cluster + "\\client.key";
        byte[] keyBytes = Files.readAllBytes(Paths.get(keyPath));
        // Remove all newline and carriage return characters
        return new String(keyBytes).replace("\n", "").replace("\r", "").trim();
    }

    private static String encode(String format, Boolean isKey) {
        //get username and access token to environment variables
        String username = System.getenv("USERNAME_ENV_VAR");
        String token = System.getenv("TOKEN_ENV_VAR");
        if (isKey){
            String password = System.getenv("PASSWORD_ENV_VAR");
            String email = System.getenv("EMAIL_ENV_VAR");
            String auth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
            return String.format(format, username, password, email, auth);
        }
        byte [] formatBytes = String.format(format, username, token).getBytes();
        return Base64.getEncoder().encodeToString(formatBytes);

    }
}
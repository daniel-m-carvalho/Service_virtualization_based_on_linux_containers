package pt.isel.leic.svlc.util.auth;

import java.util.Base64;

import static pt.isel.leic.svlc.util.kubernetes.Commands.*;
import static pt.isel.leic.svlc.util.executers.CmdExec.executeCommand;

public class Auth {

    public static String createAuthHeader() {
        String format = "{\"username\":\"%s\",\"token\":\"%s\"}";
        return encode(format);
    }

    public static String registryKey() {
        String format = "%s:%s";
        return encode(format);
    }

    public static String getKubernetesToken(String namespace) throws Exception {
        String secretName = executeCommand(secretNameCMD(namespace), "", true).getOutput();
        String encodedToken = executeCommand(tokenCMD(secretName, namespace), "", true).getOutput();

        byte[] decodedBytes = Base64.getDecoder().decode(encodedToken);
        return new String(decodedBytes);
    }

    private static String encode(String format){
        //get username and access token to environment variables
        String username = System.getenv("USERNAME_ENV_VAR");
        String token = System.getenv("TOKEN_ENV_VAR");
        return Base64.getEncoder().encodeToString(String.format(format, username, token).getBytes());
    }
}
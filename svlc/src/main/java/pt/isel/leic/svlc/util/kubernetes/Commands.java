package pt.isel.leic.svlc.util.kubernetes;

public class Commands {

    public static String[] secretNameCMD(String namespace) {
        return new String[]{"kubectl", "get", "serviceaccount", "default", "-n", namespace, "-o", "jsonpath={.secrets[0].name}"};
    }

    public static String[] tokenCMD(String secretName, String namespace) {
        return new String[]{"kubectl", "get", "secret", secretName, "-n", namespace, "-o", "jsonpath={.data.token}"};
    }

}

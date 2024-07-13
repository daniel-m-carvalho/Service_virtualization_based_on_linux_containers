package pt.isel.leic.svlc.util.kubernetes;

public class Commands {

    public static String secretNameCMD(String namespace) {
        return "kubectl get serviceaccount default -n " + namespace + " -o jsonpath=\"{.secrets[0].name}\"";
    }

    public static String tokenCMD(String secretName, String namespace) {
        return "kubectl get secret " + secretName + " -n " + namespace + " -o jsonpath=\"{.data.token}\"";
    }
}

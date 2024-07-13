package pt.isel.leic.svlc.util.executers;

import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.Builder;

@FunctionalInterface
public interface ReqBuilder {
    Builder apply(Builder builder, BodyPublisher body) throws Exception;
}

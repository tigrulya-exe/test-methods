package org.nsu.fit.services.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.glassfish.jersey.client.ClientConfig;
import org.nsu.fit.services.log.Logger;
import org.nsu.fit.services.rest.data.AccountTokenPojo;
import org.nsu.fit.shared.JsonMapper;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.BiFunction;

import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SERVER_ERROR;

@RequiredArgsConstructor
public class HttpClient {

    private final String baseUrl;

    private final Client client = ClientBuilder.newClient(
            new ClientConfig().register(RestClientLogFilter.class)
    );

    public <R> R get(String path, Class<R> responseType, AccountTokenPojo accountToken) {
        return doJsonRequest((req, ignored) -> req.get(), path, null, responseType, accountToken);
    }

    // TODO: соре Саня, мне было лень чето придумывать
    public <R> R get(String path, TypeReference<R> responseType, AccountTokenPojo accountToken) {
        return doJsonRequest((req, ignored) -> req.get(), path, null, responseType, accountToken);
    }

    public <R> R delete(String path, Class<R> responseType, AccountTokenPojo accountToken) {
        return doJsonRequest((req, ignored) -> req.delete(), path, null, responseType, accountToken);
    }

    public <R> R post(String path, String body, Class<R> responseType, AccountTokenPojo accountToken) {
        return doJsonRequest(Invocation.Builder::post, path, body, responseType, accountToken);
    }

    private <R> R doJsonRequest(
            BiFunction<Invocation.Builder, Entity<?>, Response> methodHandler,
            String path,
            String body,
            Class<R> responseType,
            AccountTokenPojo accountToken
    ) {
        return doJsonRequest(methodHandler, path, body, new TypeReference<R>() {
            @Override
            public Type getType() {
                return responseType;
            }
        }, accountToken);
    }

    private <R> R doJsonRequest(
            BiFunction<Invocation.Builder, Entity<?>, Response> methodHandler,
            String path,
            String body,
            TypeReference<R> responseType,
            AccountTokenPojo accountToken
    ) {
        // Лабораторная 3: Добавить обработку Responses и Errors. Выводите их в лог.
        // Подумайте почему в filter нет Response чтобы можно было удобно его сохранить.
        Invocation.Builder request = client
                .target(baseUrl)
                .path(path)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        if (accountToken != null) {
            request.header("Authorization", "Bearer " + accountToken.token);
        }

        Response response = methodHandler.apply(request, Entity.entity(body, MediaType.APPLICATION_JSON));
        String jsonBody = getBody(response, String.class);

        Logger.debug("Response body --" + jsonBody);
        return Optional.of(jsonBody)
                .filter(str -> !str.isEmpty())
                .map(b -> JsonMapper.fromJson(jsonBody, responseType))
                .orElse(null);
    }

    private static <V> V getBody(Response response, Class<V> clazz) {
        Optional<Response> responseOptional = Optional.ofNullable(response);
        boolean isError = responseOptional.map(Response::getStatusInfo)
                .map(Response.StatusType::getFamily)
                .filter(family -> CLIENT_ERROR.equals(family) || SERVER_ERROR.equals(family))
                .isPresent();

        if (isError) {
            RuntimeException exc = new RuntimeException(
                    responseOptional.map(resp -> resp.readEntity(String.class))
                            .orElse("")
            );
            Logger.error("Error during request: ", exc);
            throw exc;
        }

        return responseOptional.map(resp -> resp.readEntity(clazz))
                .orElse(null);
    }

    private static class RestClientLogFilter implements ClientRequestFilter {
        @Override
        public void filter(ClientRequestContext requestContext) {
            Logger.debug("Request method -- " + requestContext.getMethod());
            Logger.debug("Request headers --" + requestContext.getHeaders());
            Logger.debug("Request body --" + requestContext.getEntity());
        }
    }
}

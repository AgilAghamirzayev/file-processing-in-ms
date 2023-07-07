package com.ingress.fileuploadms.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingress.fileuploadms.exception.ResourceNotFoundException;
import com.ingress.fileuploadms.exception.ForbiddenAccessException;
import com.ingress.fileuploadms.exception.handling.ExceptionMessage;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static feign.Util.toByteArray;

@Log4j2
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder delegate = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpHeaders responseHeaders = new HttpHeaders();
        response.headers().forEach((key, value) -> responseHeaders.put(key, new ArrayList<>(value)));
        HttpStatus statusCode = HttpStatus.valueOf(response.status());
        String statusText = response.reason();
        byte[] responseBody = getResponseBody(response);
        String message = decodeResponse(response);


        if (response.status() >= 400 && response.status() <= 499) {
            return switch (response.status()) {
                case 404 -> new ResourceNotFoundException(message == null ? "File no found" : message);
                case 403 -> new ForbiddenAccessException(message == null ? "Forbidden access" : message);
                default -> new HttpClientErrorException(statusCode, statusText, responseHeaders, responseBody, null);
            };
        }

        if (response.status() >= 500 && response.status() <= 599) {
            return new HttpServerErrorException(statusCode, statusText, responseHeaders, responseBody, null);
        }

        return delegate.decode(methodKey, response);
    }

    private static byte[] getResponseBody(Response response) {
        byte[] responseBody;
        try {
            responseBody = toByteArray(response.body().asInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to process response body.", e);
        }
        return responseBody;
    }

    private static String decodeResponse(Response response) {
        String message = null;

        try (Reader reader = response.body().asReader(StandardCharsets.UTF_8)) {
            String result = Util.toString(reader);

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            ExceptionMessage exceptionMessage = mapper.readValue(result, ExceptionMessage.class);
            message = exceptionMessage.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.error(message);

        return message;
    }

}

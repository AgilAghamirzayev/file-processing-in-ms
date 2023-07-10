package com.ingress.fileuploadms.client.decoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.ingress.fileuploadms.exception.CustomFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import static com.ingress.fileuploadms.client.decoder.JsonNodeFieldName.CODE;
import static com.ingress.fileuploadms.exception.handling.ExceptionMessage.CLIENT_ERROR;
import static com.ingress.fileuploadms.util.MapperUtil.MAPPER_UTIL;

@Log4j2
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        var errorMessage = CLIENT_ERROR.getMessage();
        var status = response.status();

        JsonNode jsonNode;

        try(var body = response.body().asInputStream()) {
            jsonNode = MAPPER_UTIL.map(body, JsonNode.class);

        } catch (Exception e) {
            throw new CustomFeignException(errorMessage, status);
        }

        if (jsonNode.has(CODE.getValue())) errorMessage = jsonNode.get(CODE.getValue()).asText();

        log.error("ActionLog.decode.error Message: {}, Method: {}", errorMessage, methodKey);
        return new CustomFeignException(errorMessage, status);
    }
}

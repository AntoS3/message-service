package com.messageservice.config;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class CustomDecoder extends ResponseEntityDecoder {

    public CustomDecoder(Decoder decoder) {
        super(decoder);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        return super.decode(response, type);
    }

    private static class CustomClientHttpResponse implements ClientHttpResponse {

        private final Response response;

        private CustomClientHttpResponse(Response response) {
            this.response = response;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.valueOf(response.status());
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return response.status();
        }

        @Override
        public String getStatusText() throws IOException {
            return response.reason();
        }

        @Override
        public void close() {
            response.close();
        }

        @Override
        public InputStream getBody() throws IOException {
            return response.body().asInputStream();
        }

        @Override
        public HttpHeaders getHeaders() {
            var headers = new HttpHeaders();
            response.headers().forEach((key, value) -> headers.put(key, (List<String>) value));
            return headers;
        }
    }
}

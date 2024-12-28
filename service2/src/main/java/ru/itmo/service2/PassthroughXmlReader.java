package ru.itmo.service2;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import static jakarta.ws.rs.core.MediaType.APPLICATION_XML;

@Provider
@Consumes(APPLICATION_XML)
public class PassthroughXmlReader implements MessageBodyReader<String> {
    @Override
    public boolean isReadable(Class<?> cl, Type t, Annotation[] a, MediaType m) {
        return cl == String.class;
    }

    @Override
    public String readFrom(
        Class<String> cl,
        Type t, Annotation[] a,
        MediaType m,
        MultivaluedMap<String, String> map,
        InputStream stream
    ) throws IOException, WebApplicationException {
        return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    }
}

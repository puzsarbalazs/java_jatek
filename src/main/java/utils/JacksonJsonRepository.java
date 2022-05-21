package utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public abstract class JacksonJsonRepository {
    protected static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Deserializes a set of objects from JSON.
     *
     * @param in           the input stream from which JSON data will be read
     * @param elementClass represents the class of the elements
     * @return the set of objects deserialized from JSON
     * @throws IOException if any I/O error occurs
     */
    protected static <T> Set<T> readSet(InputStream in, Class<T> elementClass) throws IOException {
        JavaType type = MAPPER.getTypeFactory().constructCollectionType(Set.class, elementClass);
        return MAPPER.readValue(in, type);
    }

    /**
     * Serializes a set of objects to JSON.
     * @param os the output stream to which JSON data will be written
     * @param value represents the class of the elements
     * @throws IOException if any I/O error occurs
     */
    protected static <T> void writeSet(OutputStream os, Set<T> value) throws IOException {
        MAPPER.writeValue(os, value);
    }
}

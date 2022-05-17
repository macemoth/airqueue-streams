package ch.unisg.airqueue.serialisation;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Freely taken from lab13
 */
public class JsonDeserialiser<T> implements Deserializer<T> {

    private Gson gson =
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

    private Class<T> destinationClass;
    private Type reflectionTypeToken;

    public JsonDeserialiser(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    public JsonDeserialiser(Type reflectionTypeToken) {
        this.reflectionTypeToken = reflectionTypeToken;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // do nothing
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Type type = destinationClass != null ? destinationClass : reflectionTypeToken;
        return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), type);
    }

    @Override
    public void close() {
        // do nothing
    }
}

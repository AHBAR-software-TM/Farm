package backend;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;

public class WildAnimalDeserial extends StdDeserializer<Wild_animal> {
    public WildAnimalDeserial() {
        this(null);
    }

    public WildAnimalDeserial(Class<?> vc) {
        super(vc);
    }

    @Override
    public Wild_animal deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode jn = jp.getCodec().readTree(jp);
        int price = (Integer) ((IntNode) jn.get("price")).numberValue();
        if(price == 500)
            return new Tiger();
        if (price==300)
            return new Lion();
        else return new Bear();

    }

}

package com.figtreelake.util.time.duration.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Duration;

/**
 * <p>
 * A {@link StdDeserializer} extension to deserialize {@link Duration} objects from an ISO-8601
 * formatted text.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link DurationToIso8601FormatSerializer} class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class DurationFromIso8601FormatDeserializer extends StdDeserializer<Duration> {

  private static final long serialVersionUID = 1L;

  public DurationFromIso8601FormatDeserializer() {
    super(Duration.class);
  }

  @Override
  public Duration deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    return Duration.parse(jsonParser.getText());
  }

}

package com.figtreelake.util.time.duration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.Duration;

/**
 * <p>
 * A {@link StdSerializer} extension to serialize {@link Duration} objects to a ISO-8601 formatted
 * text.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link DurationFromIso8601FormatDeserializer}
 * class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class DurationToIso8601FormatSerializer extends StdSerializer<Duration> {

  private static final long serialVersionUID = 1L;

  public DurationToIso8601FormatSerializer() {
    super(Duration.class);
  }

  @Override
  public void serialize(Duration duration, JsonGenerator jsonGenerator,
      SerializerProvider serializer) throws IOException {
    jsonGenerator.writeString(duration.toString());
  }

}

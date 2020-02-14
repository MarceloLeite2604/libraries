package com.figtreelake.util.time.local.serializer.iso;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalTime;

/**
 * <p>
 * A {@link StdSerializer} extension which helps serialization of {@link LocalTime} objects to a
 * predefined text format.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link LocalTimeFromIso8601FormatDeserializer}
 * class.
 * </p>
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class LocalTimeToIso8601FormatSerializer extends StdSerializer<LocalTime> {

  private static final long serialVersionUID = 1L;

  public LocalTimeToIso8601FormatSerializer() {
    super(LocalTime.class);
  }

  @Override
  public void serialize(LocalTime localTime, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeString(localTime.toString());
  }

}

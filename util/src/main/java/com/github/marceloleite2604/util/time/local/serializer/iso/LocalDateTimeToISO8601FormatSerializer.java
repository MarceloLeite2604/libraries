package com.github.marceloleite2604.util.time.local.serializer.iso;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * A {@link StdSerializer} extension to serialize {@link LocalDateTime} objects to an ISO-8601
 * formatted date text.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on
 * {@link LocalDateTimeFromISO8601FormatDeserializer} class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class LocalDateTimeToISO8601FormatSerializer extends StdSerializer<LocalDateTime> {

  private static final long serialVersionUID = 1L;

  public LocalDateTimeToISO8601FormatSerializer() {
    super(LocalDateTime.class);
  }

  @Override
  public void serialize(LocalDateTime localDateTime, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    generator.writeString(localDateTime.toString());
  }

}

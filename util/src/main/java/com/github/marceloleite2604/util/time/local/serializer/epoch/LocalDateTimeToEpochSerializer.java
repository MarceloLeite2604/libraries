package com.github.marceloleite2604.util.time.local.serializer.epoch;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.local.LocalDateTimeUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * <p>
 * A {@link StdSerializer} extension which helps serialization of {@link ZonedDateTime} objects to
 * epoch time format on UTF zone offset.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link LocalDateTimeFromEpochDeserializer} class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class LocalDateTimeToEpochSerializer extends StdSerializer<LocalDateTime> {

  private static final long serialVersionUID = 1L;

  private final transient LocalDateTimeUtil localDateTimeUtil;

  public LocalDateTimeToEpochSerializer() {
    super(LocalDateTime.class);
    this.localDateTimeUtil = new LocalDateTimeUtil();
  }

  @Override
  public void serialize(LocalDateTime localDateTime, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    generator.writeNumber(localDateTimeUtil.convertAsEpochTimeOnUTCZoneOffset(localDateTime));
  }

}

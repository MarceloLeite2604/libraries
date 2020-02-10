package com.github.marceloleite2604.util.time.local.serializer.timestamp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.local.LocalDateTimeUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * <p>
 * A {@link StdDeserializer} extension which helps deserialization of {@link ZonedDateTime} objects
 * from timestamp values.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link LocalDateTimeToTimestampTextSerializer}
 * class.
 * </p>
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class LocalDateTimeFromTimestampTextDeserializer extends StdDeserializer<LocalDateTime> {

  private static final long serialVersionUID = 1L;

  private final transient LocalDateTimeUtil localDateTimeUtil;

  public LocalDateTimeFromTimestampTextDeserializer() {
    super(LocalDateTime.class);
    this.localDateTimeUtil = new LocalDateTimeUtil();
  }

  @Override
  public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    return localDateTimeUtil.parseFromTimestamp(jsonParser.getValueAsString());
  }

}

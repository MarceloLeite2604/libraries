package com.github.marceloleite2604.util.time.local.serializer.iso;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.local.LocalDateTimeUtil;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * A {@link StdDeserializer} extension which helps on ISO 8601 date text deserialization to a
 * {@link LocalDateTime} object.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link LocalDateTimeToISO8601FormatSerializer}
 * class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class LocalDateTimeFromISO8601FormatDeserializer extends StdDeserializer<LocalDateTime> {

  private static final long serialVersionUID = 1L;

  private final transient LocalDateTimeUtil localDateTimeUtil;

  public LocalDateTimeFromISO8601FormatDeserializer() {
    super(LocalDateTime.class);
    this.localDateTimeUtil = new LocalDateTimeUtil();
  }

  @Override
  public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    return localDateTimeUtil.parseFromISO8601(jsonParser.getText());
  }

}

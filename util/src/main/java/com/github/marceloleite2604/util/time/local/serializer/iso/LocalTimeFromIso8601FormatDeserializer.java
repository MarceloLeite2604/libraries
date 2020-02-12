package com.github.marceloleite2604.util.time.local.serializer.iso;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.local.LocalTimeUtil;
import java.io.IOException;
import java.time.LocalTime;

/**
 * <p>
 * A {@link StdDeserializer} deserializes {@link LocalTime} objects from ISO-8601 formated date
 * text.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link LocalTimeToIso8601FormatSerializer} class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class LocalTimeFromIso8601FormatDeserializer extends StdDeserializer<LocalTime> {

  private static final long serialVersionUID = 1L;

  private final transient LocalTimeUtil localTimeUtil;

  public LocalTimeFromIso8601FormatDeserializer() {
    super(LocalTime.class);
    this.localTimeUtil = new LocalTimeUtil();
  }

  @Override
  public LocalTime deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    return localTimeUtil.parseFromIso8601Format(jsonParser.getText());
  }

}

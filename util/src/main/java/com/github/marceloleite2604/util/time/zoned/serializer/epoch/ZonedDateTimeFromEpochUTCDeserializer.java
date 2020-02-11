package com.github.marceloleite2604.util.time.zoned.serializer.epoch;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.zoned.ZonedDateTimeUtil;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * <p>
 * A {@link StdDeserializer} deserializes epoch time on UTC zone offset to {@link ZonedDateTime} a
 * object.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link ZonedDateTimeToEpochUTCSerializer} class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class ZonedDateTimeFromEpochUTCDeserializer extends StdDeserializer<ZonedDateTime> {

  private static final long serialVersionUID = 1L;

  private final transient ZonedDateTimeUtil zonedDateTimeUtil;

  public ZonedDateTimeFromEpochUTCDeserializer() {
    super(ZonedDateTime.class);
    this.zonedDateTimeUtil = new ZonedDateTimeUtil();
  }

  @Override
  public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    return zonedDateTimeUtil.convertFromEpochTimeToUTFOffset(jsonParser.getValueAsLong());
  }

}

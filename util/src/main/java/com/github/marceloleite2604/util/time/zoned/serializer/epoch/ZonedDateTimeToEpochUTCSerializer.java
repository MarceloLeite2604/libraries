package com.github.marceloleite2604.util.time.zoned.serializer.epoch;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.zoned.ZonedDateTimeUtil;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * <p>
 * A {@link StdSerializer} serializes {@link ZonedDateTime} objects to epoch time on UTC offset.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link ZonedDateTimeFromEpochUTCDeserializer}
 * class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class ZonedDateTimeToEpochUTCSerializer extends StdSerializer<ZonedDateTime> {

  private static final long serialVersionUID = 1L;

  private final transient ZonedDateTimeUtil zonedDateTimeUtil;

  public ZonedDateTimeToEpochUTCSerializer() {
    super(ZonedDateTime.class);
    this.zonedDateTimeUtil = new ZonedDateTimeUtil();
  }

  @Override
  public void serialize(ZonedDateTime zonedDateTime, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    generator.writeNumber(zonedDateTimeUtil.convertAsEpochTime(zonedDateTime));
  }

}

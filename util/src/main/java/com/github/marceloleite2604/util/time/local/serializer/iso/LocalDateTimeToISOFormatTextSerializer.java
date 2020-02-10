package com.github.marceloleite2604.util.time.local.serializer.iso;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.zoned.ZonedDateTimeUtil;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * <p>
 * A {@link StdSerializer} extension which helps serialization of {@link ZonedDateTime} objects to a
 * predefined text format.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link LocalDateTimeFromISOFormatTextDeserializer} class.
 * </p>
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class LocalDateTimeToISOFormatTextSerializer extends StdSerializer<ZonedDateTime> {

  private static final long serialVersionUID = 1L;

  private final transient ZonedDateTimeUtil zonedDateTimeUtil;

  public LocalDateTimeToISOFormatTextSerializer() {
    super(ZonedDateTime.class);
    this.zonedDateTimeUtil = new ZonedDateTimeUtil();
  }

  @Override
  public void serialize(ZonedDateTime zonedDateTime, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    generator.writeString(zonedDateTimeUtil.toStringAsISOOffsetDateTime(zonedDateTime));
  }

}

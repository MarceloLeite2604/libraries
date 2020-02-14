package com.figtreelake.util.time.zoned.serializer.iso;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.figtreelake.util.time.zoned.ZonedDateTimeUtil;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * <p>
 * A {@link StdSerializer} serializes a {@link ZonedDateTime} object to aa ISO-8601 formatted date
 * text.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link ZonedDateTimeFromIso8601FormatDeserializer}
 * class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class ZonedDateTimeToIsoOffsetFormatTextSerializer extends StdSerializer<ZonedDateTime> {

  private static final long serialVersionUID = 1L;

  private final transient ZonedDateTimeUtil zonedDateTimeUtil;

  public ZonedDateTimeToIsoOffsetFormatTextSerializer() {
    super(ZonedDateTime.class);
    this.zonedDateTimeUtil = new ZonedDateTimeUtil();
  }

  @Override
  public void serialize(ZonedDateTime zonedDateTime, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    generator.writeString(zonedDateTimeUtil.toStringAsIsoOffsetDateTime(zonedDateTime));
  }

}

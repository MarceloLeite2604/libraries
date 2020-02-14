package com.figtreelake.util.time.zoned.serializer.iso;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.figtreelake.util.time.zoned.ZonedDateTimeUtil;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * <p>
 * A {@link StdDeserializer} deserializes a {@link ZonedDateTime} object from an ISO-8601 formatted
 * date text.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link ZonedDateTimeToIsoOffsetFormatTextSerializer}
 * class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class ZonedDateTimeFromIso8601FormatDeserializer extends StdDeserializer<ZonedDateTime> {

  private static final long serialVersionUID = 1L;

  private final transient ZonedDateTimeUtil zonedDateTimeUtil;

  public ZonedDateTimeFromIso8601FormatDeserializer() {
    super(ZonedDateTime.class);
    this.zonedDateTimeUtil = new ZonedDateTimeUtil();
  }

  @Override
  public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    return zonedDateTimeUtil.parseFromIsoOffsetFormat(jsonParser.getText());
  }

}

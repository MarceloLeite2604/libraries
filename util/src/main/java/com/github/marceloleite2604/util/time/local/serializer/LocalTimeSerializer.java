package com.github.marceloleite2604.util.time.local.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.local.LocalTimeUtil;
import java.io.IOException;
import java.time.LocalTime;

/**
 * <p>
 * A {@link StdSerializer} extension which helps serialization of {@link LocalTime} objects to a
 * predefined text format.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link LocalTimeDeserializer} class.
 * </p>
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class LocalTimeSerializer extends StdSerializer<LocalTime> {

  private static final long serialVersionUID = 1L;

  private final transient LocalTimeUtil localTimeUtil;

  public LocalTimeSerializer() {
    super(LocalTime.class);
    this.localTimeUtil = new LocalTimeUtil();
  }

  @Override
  public void serialize(LocalTime localTime, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeString(localTimeUtil.toString(localTime));
  }

}

package com.github.marceloleite2604.util.time.duration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.duration.DurationUtil;
import java.io.IOException;
import java.time.Duration;

/**
 * <p>
 * A {@link StdSerializer} extension to serializes {@link Duration} objects to its amount of
 * seconds.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link DurationFromSecondsDeserializer} class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class DurationToSecondsSerializer extends StdSerializer<Duration> {

  private static final long serialVersionUID = 1L;

  private final transient DurationUtil durationUtil;

  public DurationToSecondsSerializer() {
    super(Duration.class);
    this.durationUtil = new DurationUtil();
  }

  @Override
  public void serialize(Duration duration, JsonGenerator jsonGenerator,
      SerializerProvider serializer) throws IOException {
    jsonGenerator.writeNumber(durationUtil.formatAsSeconds(duration));
  }

}

package com.github.marceloleite2604.util.time.duration.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.duration.DurationUtil;
import java.io.IOException;
import java.time.Duration;

/**
 * <p>
 * A {@link StdDeserializer} extension which helps deserialization of {@link Duration} objects from
 * a predefined text format.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link DurationSerializer} class.
 * </p>
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class DurationDeserializer extends StdDeserializer<Duration> {

  private static final long serialVersionUID = 1L;

  private final transient DurationUtil durationUtil;

  public DurationDeserializer() {
    super(Duration.class);
    this.durationUtil = new DurationUtil();
  }

  @Override
  public Duration deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    return durationUtil.parseFromSeconds(jsonParser.getLongValue());
  }

}

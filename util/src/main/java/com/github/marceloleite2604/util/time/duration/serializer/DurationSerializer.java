package com.github.marceloleite2604.util.time.duration.serializer;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.duration.DurationUtil;

/**
 * <p>
 * A {@link StdSerializer} extension which helps serialization of
 * {@link Duration} objects to a predefined text format.
 * </p>
 * <p>
 * Its deserialization equivalent can be found on {@link DurationDeserializer}
 * class.
 * </p>
 * 
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target=
 *      "_top">GitHub project</a>
 * 
 * @author MarceloLeite2604
 * 
 */
public class DurationSerializer extends StdSerializer<Duration> {

	private static final long serialVersionUID = 1L;

	private final transient DurationUtil durationUtil;

	public DurationSerializer() {
		super(Duration.class);
		this.durationUtil = new DurationUtil();
	}

	@Override
	public void serialize(Duration duration, JsonGenerator jsonGenerator,
			SerializerProvider serializer) throws IOException {
		jsonGenerator.writeNumber(durationUtil.formatAsSeconds(duration));
	}

}

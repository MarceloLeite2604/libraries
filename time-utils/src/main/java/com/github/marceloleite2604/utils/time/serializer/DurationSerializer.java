package com.github.marceloleite2604.utils.time.serializer;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.utils.time.DurationUtil;

public class DurationSerializer extends StdSerializer<Duration> {

	private static final long serialVersionUID = 1L;

	public DurationSerializer() {
		super(Duration.class);
	}

	@Override
	public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider serializer)
			throws IOException {
		jsonGenerator.writeNumber(DurationUtil.formatAsSeconds(duration));
	}

}

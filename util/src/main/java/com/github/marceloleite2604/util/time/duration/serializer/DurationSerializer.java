package com.github.marceloleite2604.util.time.duration.serializer;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.duration.DurationUtil;

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

package com.github.marceloleite2604.utils.time.serializer;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.utils.time.LocalTimeUtil;

public class LocalTimeSerializer extends StdSerializer<LocalTime> {

	public LocalTimeSerializer() {
		super(LocalTime.class);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(LocalTime localTime, JsonGenerator generator, SerializerProvider provider)
			throws IOException {
		generator.writeString(LocalTimeUtil.format(localTime));
	}

}

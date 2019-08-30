package com.github.marceloleite2604.util.time.local.serializer;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.local.LocalTimeUtil;

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

package com.github.marceloleite2604.util.time.deserializer;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.LocalTimeUtil;

public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

	private static final long serialVersionUID = 1L;

	public LocalTimeDeserializer() {
		super(LocalTime.class);
	}

	@Override
	public LocalTime deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		return LocalTimeUtil.parse(jsonParser.getText());
	}

}

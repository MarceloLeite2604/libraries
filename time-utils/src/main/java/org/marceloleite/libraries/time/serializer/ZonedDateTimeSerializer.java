package org.marceloleite.libraries.time.serializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import org.marceloleite.libraries.time.util.ZonedDateTimeUtil;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ZonedDateTimeSerializer extends StdSerializer<ZonedDateTime> {

	public ZonedDateTimeSerializer() {
		super(ZonedDateTime.class);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(ZonedDateTime zonedDateTime, JsonGenerator generator, SerializerProvider provider)
			throws IOException {
		generator.writeString(ZonedDateTimeUtil.format(zonedDateTime));
	}

}
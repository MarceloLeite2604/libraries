package com.github.marceloleite2604.util.time.local.serializer;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.local.LocalTimeUtil;

/**
 * <p>
 * A {@link StdDeserializer} extension which helps deserialization of
 * {@link LocalTime} objects from a predefined text format.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link LocalTimeSerializer}
 * class.
 * </p>
 * 
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target=
 *      "_top">GitHub project</a>
 * 
 * @author MarceloLeite2604
 * 
 */
public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

	private static final long serialVersionUID = 1L;

	private final transient LocalTimeUtil localTimeUtil;

	public LocalTimeDeserializer() {
		super(LocalTime.class);
		this.localTimeUtil = new LocalTimeUtil();
	}

	@Override
	public LocalTime deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException {
		return localTimeUtil.parse(jsonParser.getText());
	}

}

package cn.klmb.crm.framework.jackson.core.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 描述：BigDecimal 序列化
 *
 * @author: 蒋雷佳
 * @date: 2023/6/9 15:10
 */
public class BigDecimalFormatSerializer extends JsonSerializer<BigDecimal> {

	private static final DecimalFormat FORMAT = new DecimalFormat("#0.00");

	@Override
	public void serialize(BigDecimal value, JsonGenerator jsonGenerator,
		SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		if (value != null) {
			value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
			jsonGenerator.writeString(value.toString());
		}
	}

}

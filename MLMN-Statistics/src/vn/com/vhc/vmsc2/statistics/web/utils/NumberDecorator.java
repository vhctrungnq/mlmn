package vn.com.vhc.vmsc2.statistics.web.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class NumberDecorator implements DisplaytagColumnDecorator {
	private NumberFormat intFormat = new DecimalFormat("#,###,###");
	private NumberFormat floatFormat = new DecimalFormat("#,###,###,##0.##");

	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		if (columnValue instanceof BigDecimal) {
			BigDecimal numBigDecimal = (BigDecimal) columnValue;
			double numDouble = numBigDecimal.doubleValue();
			return floatFormat.format(numDouble);
		} else
		if (columnValue instanceof Double) {
			Double numDouble = (Double) columnValue;
			return floatFormat.format(numDouble);
		} else
		if (columnValue instanceof Float) {
			Float numFloat = (Float) columnValue;
			double numDouble = (double) numFloat;
			return floatFormat.format(numDouble);
		} else if (columnValue instanceof Integer) {
			Integer num = (Integer) columnValue;
			return intFormat.format(num);
		}
		return columnValue;
	}
}


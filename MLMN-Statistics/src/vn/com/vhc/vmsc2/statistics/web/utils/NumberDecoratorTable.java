package vn.com.vhc.vmsc2.statistics.web.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class NumberDecoratorTable extends TableDecorator {
	private NumberFormat intFormat = new DecimalFormat("#,###,###");
	private NumberFormat floatFormat = new DecimalFormat("#,###,###,##0.##"); 
	
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
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


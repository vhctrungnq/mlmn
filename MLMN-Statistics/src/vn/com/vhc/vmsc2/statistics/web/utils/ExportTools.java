package vn.com.vhc.vmsc2.statistics.web.utils;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class ExportTools {

	// Create cellFormat Header
	public static WritableCellFormat getCellFormatHeader(WritableCellFormat cellFormatHeader) throws WriteException {
		cellFormatHeader.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatHeader.setBackground(Colour.GREEN, Pattern.SOLID);
		cellFormatHeader.setAlignment(Alignment.CENTRE);
		// create create a bold font
	    WritableFont times10ptBold = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
	    times10ptBold.setColour(Colour.WHITE);
	    cellFormatHeader.setFont(times10ptBold);
	    
		return cellFormatHeader;
	}
	
	public static WritableCellFormat getCellFormat(WritableCellFormat cellFormat) throws WriteException {
		
	    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
		return cellFormat;
	}
	
}

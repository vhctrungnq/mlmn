package vn.com.vhc.sts.converter;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.jboss.util.file.Files;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

/**
 * @author VHC-TRUNGNQ
 *	su dung cho cac file da o dang csv da co commentchar, class nay chi thuc hien viec copy file
 */
public class LazyConverter extends STS_BasicConverter{
	
	private static final Logger logger = Logger.getLogger(LazyConverter.class);
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		makeDirectory(direcPath);
		try {
			Files.copy(file, new File(direcPath, file.getName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e, e);
		}
	}

}

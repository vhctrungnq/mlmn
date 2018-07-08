package vn.com.vhc.sts.cni.converter;

import java.io.File;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class GenericConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(GenericConverter.class.getName());

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        if (file == null || direcPath == null || direcPath.length() == 0) {
            logger.warn("Original file and file path must not be null");
            throw new STS_ConvertException("Invalid parameter");
        }
        try {
            // Destination directory
            File dirFile = new File(direcPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            // Move file to new directory
            file.renameTo(new File(dirFile, file.getName()));
            logger.info("Copy file: " + file.getName() + " success");
        } catch (Exception e) {
            throw new STS_ConvertException(e.getMessage(), "VMSC2-0305", e);
        }
    }

}

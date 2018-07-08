package vn.com.vhc.sts.cni;

import java.io.File;
import java.sql.Connection;
import java.util.Hashtable;

import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public interface STS_IConverter {

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException;
    
    /**
     * 
     * @author TRUNGNQ
     * 5/9/2017 them phuong thuc convertFile cho nokia3g 
     */
    public void convertFile(File file, String direcPath, Hashtable<Byte, String> params, Connection conn)
    		throws STS_ConvertException;
            
}

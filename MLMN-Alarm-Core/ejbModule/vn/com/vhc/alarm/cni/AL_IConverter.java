package vn.com.vhc.alarm.cni;

import java.io.File;

import java.util.Hashtable;

import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;



public interface AL_IConverter {

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws AL_ConvertException;
}

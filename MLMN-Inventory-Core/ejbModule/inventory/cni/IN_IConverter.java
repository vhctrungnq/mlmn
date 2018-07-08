package inventory.cni;

import inventory.util.exceptions.IN_ConvertException;

import java.io.File;

import java.util.Hashtable;




public interface IN_IConverter {

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws IN_ConvertException;
}

package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.dbadatafiles;

public interface dbadatafilesDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DBA_DATA_FILES
     *
     * @ibatorgenerated Tue Nov 06 15:35:53 ICT 2012
     */
    void insert(dbadatafiles record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DBA_DATA_FILES
     *
     * @ibatorgenerated Tue Nov 06 15:35:53 ICT 2012
     */
    void insertSelective(dbadatafiles record);
    List<dbadatafiles> filter(String tablespaceName);
}
package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.HIpbbBwlist;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbGraphTemplateGraph;

public interface HIpbbGraphTemplateGraphDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_GRAPH_TEMPLATE_GRAPH
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_GRAPH_TEMPLATE_GRAPH
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    void insert(HIpbbGraphTemplateGraph record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_GRAPH_TEMPLATE_GRAPH
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    void insertSelective(HIpbbGraphTemplateGraph record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_GRAPH_TEMPLATE_GRAPH
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    HIpbbGraphTemplateGraph selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_GRAPH_TEMPLATE_GRAPH
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    int updateByPrimaryKeySelective(HIpbbGraphTemplateGraph record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_GRAPH_TEMPLATE_GRAPH
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    int updateByPrimaryKey(HIpbbGraphTemplateGraph record);
    
    List<HIpbbGraphTemplateGraph> getData(String title, String link, String column, int order);
    List<HIpbbBwlist> getLinkId(String link);
}
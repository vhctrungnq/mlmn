package vn.com.vhc.vmsc2.statistics.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class HIpbbGraphTemplateGraph {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.LOCAL_GRAPH_ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    private Integer localGraphId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.TITLE
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    @NotEmpty
    private String title;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.HOST_ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    private Integer hostId;
    
    private String link;
    
    private String strLocalGraphId;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.ID
     *
     * @return the value of H_IPBB_GRAPH_TEMPLATE_GRAPH.ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.ID
     *
     * @param id the value for H_IPBB_GRAPH_TEMPLATE_GRAPH.ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.LOCAL_GRAPH_ID
     *
     * @return the value of H_IPBB_GRAPH_TEMPLATE_GRAPH.LOCAL_GRAPH_ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    public Integer getLocalGraphId() {
        return localGraphId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.LOCAL_GRAPH_ID
     *
     * @param localGraphId the value for H_IPBB_GRAPH_TEMPLATE_GRAPH.LOCAL_GRAPH_ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    public void setLocalGraphId(Integer localGraphId) {
        this.localGraphId = localGraphId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.TITLE
     *
     * @return the value of H_IPBB_GRAPH_TEMPLATE_GRAPH.TITLE
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.TITLE
     *
     * @param title the value for H_IPBB_GRAPH_TEMPLATE_GRAPH.TITLE
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.HOST_ID
     *
     * @return the value of H_IPBB_GRAPH_TEMPLATE_GRAPH.HOST_ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    public Integer getHostId() {
        return hostId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_IPBB_GRAPH_TEMPLATE_GRAPH.HOST_ID
     *
     * @param hostId the value for H_IPBB_GRAPH_TEMPLATE_GRAPH.HOST_ID
     *
     * @ibatorgenerated Wed Jan 08 15:27:30 ICT 2014
     */
    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getStrLocalGraphId() {
		return strLocalGraphId;
	}

	public void setStrLocalGraphId(String strLocalGraphId) {
		this.strLocalGraphId = strLocalGraphId;
	}
}
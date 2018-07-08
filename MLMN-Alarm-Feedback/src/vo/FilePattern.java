package vo;


import org.hibernate.validator.constraints.NotEmpty;

public class FilePattern {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.PATTERN_ID
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
  private Integer patternId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.FILE_PATTERN
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
  @NotEmpty
  private String filePattern;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.NODE_TYPE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private String nodeType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.RAW_TABLE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private String rawTable;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.CONVERT_CLASS
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
   @NotEmpty
   private String convertClass;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.STATUS
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    
    private String status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.SUB_DIR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private String subDir;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.NODE_PATTERN_GROUP
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private Integer nodePatternGroup;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.TIME_PATTERN_GROUP
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private Integer timePatternGroup;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.SEPARATOR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private String separator;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.COMMENT_CHAR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private String commentChar;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.TIME_PATTERN
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private String timePattern;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column C_FILE_PATTERNS.IMPORT_RULE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private String importRule;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.PATTERN_ID
     *
     * @return the value of C_FILE_PATTERNS.PATTERN_ID
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public Integer getPatternId() {
        return patternId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.PATTERN_ID
     *
     * @param patternId the value for C_FILE_PATTERNS.PATTERN_ID
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setPatternId(Integer patternId) {
        this.patternId = patternId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.FILE_PATTERN
     *
     * @return the value of C_FILE_PATTERNS.FILE_PATTERN
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getFilePattern() {
        return filePattern;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.FILE_PATTERN
     *
     * @param filePattern the value for C_FILE_PATTERNS.FILE_PATTERN
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.NODE_TYPE
     *
     * @return the value of C_FILE_PATTERNS.NODE_TYPE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.NODE_TYPE
     *
     * @param nodeType the value for C_FILE_PATTERNS.NODE_TYPE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.RAW_TABLE
     *
     * @return the value of C_FILE_PATTERNS.RAW_TABLE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getRawTable() {
        return rawTable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.RAW_TABLE
     *
     * @param rawTable the value for C_FILE_PATTERNS.RAW_TABLE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setRawTable(String rawTable) {
        this.rawTable = rawTable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.CONVERT_CLASS
     *
     * @return the value of C_FILE_PATTERNS.CONVERT_CLASS
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getConvertClass() {
        return convertClass;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.CONVERT_CLASS
     *
     * @param convertClass the value for C_FILE_PATTERNS.CONVERT_CLASS
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setConvertClass(String convertClass) {
        this.convertClass = convertClass;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.STATUS
     *
     * @return the value of C_FILE_PATTERNS.STATUS
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.STATUS
     *
     * @param status the value for C_FILE_PATTERNS.STATUS
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.SUB_DIR
     *
     * @return the value of C_FILE_PATTERNS.SUB_DIR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getSubDir() {
        return subDir;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.SUB_DIR
     *
     * @param subDir the value for C_FILE_PATTERNS.SUB_DIR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setSubDir(String subDir) {
        this.subDir = subDir;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.NODE_PATTERN_GROUP
     *
     * @return the value of C_FILE_PATTERNS.NODE_PATTERN_GROUP
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public Integer getNodePatternGroup() {
        return nodePatternGroup;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.NODE_PATTERN_GROUP
     *
     * @param nodePatternGroup the value for C_FILE_PATTERNS.NODE_PATTERN_GROUP
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setNodePatternGroup(Integer nodePatternGroup) {
        this.nodePatternGroup = nodePatternGroup;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.TIME_PATTERN_GROUP
     *
     * @return the value of C_FILE_PATTERNS.TIME_PATTERN_GROUP
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public Integer getTimePatternGroup() {
        return timePatternGroup;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.TIME_PATTERN_GROUP
     *
     * @param timePatternGroup the value for C_FILE_PATTERNS.TIME_PATTERN_GROUP
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setTimePatternGroup(Integer timePatternGroup) {
        this.timePatternGroup = timePatternGroup;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.SEPARATOR
     *
     * @return the value of C_FILE_PATTERNS.SEPARATOR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.SEPARATOR
     *
     * @param separator the value for C_FILE_PATTERNS.SEPARATOR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.COMMENT_CHAR
     *
     * @return the value of C_FILE_PATTERNS.COMMENT_CHAR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getCommentChar() {
        return commentChar;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.COMMENT_CHAR
     *
     * @param commentChar the value for C_FILE_PATTERNS.COMMENT_CHAR
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setCommentChar(String commentChar) {
        this.commentChar = commentChar;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.TIME_PATTERN
     *
     * @return the value of C_FILE_PATTERNS.TIME_PATTERN
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getTimePattern() {
        return timePattern;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.TIME_PATTERN
     *
     * @param timePattern the value for C_FILE_PATTERNS.TIME_PATTERN
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column C_FILE_PATTERNS.IMPORT_RULE
     *
     * @return the value of C_FILE_PATTERNS.IMPORT_RULE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    public String getImportRule() {
        return importRule;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column C_FILE_PATTERNS.IMPORT_RULE
     *
     * @param importRule the value for C_FILE_PATTERNS.IMPORT_RULE
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    private String nodePatternGroupStr;
    private String timePatternGroupStr;
    
    public void setImportRule(String importRule) {
        this.importRule = importRule;
    }

   public String getNodePatternGroupStr() {
		return nodePatternGroupStr;
	}

	public void setNodePatternGroupStr(String nodePatternGroupStr) {
		this.nodePatternGroupStr = nodePatternGroupStr;
	}

	public String getTimePatternGroupStr() {
		return timePatternGroupStr;
	}

	public void setTimePatternGroupStr(String timePatternGroupStr) {
		this.timePatternGroupStr = timePatternGroupStr;
	}

	
    
}
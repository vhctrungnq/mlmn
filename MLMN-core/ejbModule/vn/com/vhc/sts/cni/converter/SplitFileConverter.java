package vn.com.vhc.sts.cni.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class SplitFileConverter extends STS_BasicConverter {

    public static final int EXPORT_BY_BLOCK = 1;
    public static final int EXPORT_BY_LINE = 2;

    public static final int SPLIT_BY_EMPTYLINE = 1;
    public static final int SPLIT_BY_KEYWORD = 2;

    private static Logger logger =
        Logger.getLogger(SplitFileConverter.class.getName());

    private List<BlockContent> blockContents = null;
    private List<List<String>> data = new ArrayList<List<String>>();
    private List<String> excessData = new ArrayList<String>();

    private int exportType = 0;
    private int splitType = 0;
    private int startLinePos = 0;
    private boolean isCopy = true;

    private long fileId = 0;
    private String rawTable = "";


    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        makeDirectory(direcPath);
        prepareParams(params);

        init();
        prepare();

        if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
            fileId = Long.parseLong(params.get(STS_Setting.FILE_ID_KEY));
        }
        if (params.containsKey(STS_Setting.RAW_TABLE_KEY)) {
            rawTable = params.get(STS_Setting.RAW_TABLE_KEY);
        }

        try {
            //get and selection data in the file
            readContent(file);
            //none data to export in file
            if (data.isEmpty()) {
                updateRecordFlag();
                return;
            }
            refreshBlockContent(excessData);
            //write data to files
            writeContent(direcPath, file.getName());
            updateRecordFlag();
        } catch (Exception e) {
            throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
        }

        logger.warn("Convert file: " + file.getName() + " success");
    }

    private void readContent(File file) throws Exception {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            //to do something
            String line = "";
            if (isSplitByEmptyline()) {
                boolean newBlock = true;
                List<String> block = null;
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    count++;
                    if (count < this.getStartLinePos()) {
                        excessData.add(line);
                        continue;
                    }
                    newBlock = line.trim().isEmpty();
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    if (newBlock) {
                        block = new ArrayList<String>();
                        data.add(block);
                    }
                    block.add(line);
                }
            } else if (isSplitByKeyWord()) {

            }

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.warn("Close IO stream failure " + e);
                }
            }
        }
    }

    private void writeContent(String direcPath,
                              String originalName) throws Exception {
        List<String> block = null;
        BufferedWriter writer = null;
        File outFile = null;

        int index = -1;
        String fileName = "";
        String header = "";
        for (BlockContent content : blockContents) {
            index = content.getBlockIndex();
            if (index >= 0 && index < data.size()) {
                block = data.get(index);
            }
            if (block == null) {
                continue;
            }
            try {
                fileName = getFileName(content.getSplitKey(), originalName);
                outFile = new File(direcPath, fileName);
                writer = new BufferedWriter(new FileWriter(outFile, true));

                header = getColumnNames(content, block);
                writeHeader(writer, header);
                writeBlock(writer, block);


            } catch (Exception e) {
                logger.warn("Write block data [" + content.getName() +
                               "] failure: " + e.getMessage());
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        logger.warn("Close IO stream failure " + e);
                    }
                }
            }
        }
    }

    /**
     * May be overwrite this method to do something before start convert.
     * @return
     */
    public void init() {
        setExportType(EXPORT_BY_BLOCK);
        setSplitType(SPLIT_BY_EMPTYLINE);

        setCopyOfOriginal(true);
        setDefaultBlockContent(true);
    }

    /**
     * @param excessData
     */
    public void refreshBlockContent(List<String> excessData) {
        //to do something
    }

    private void prepare() throws STS_ConvertException {
        data.clear();
        excessData.clear();
        validateBlockContent();
    }

    private boolean validateBlockContent() throws STS_ConvertException {
        if (blockContents == null || blockContents.isEmpty()) {
            throw new STS_ConvertException("None config block content desciption for split file progress");
        }
        for (BlockContent content : blockContents) {
            if (!isCopyOfOriginal() &&
                content.getRawTable().trim().isEmpty()) {
                throw new STS_ConvertException("Raw tablle for block content is empty");
            }
        }

        return true;
    }

    public void writeHeader(BufferedWriter writer,
                            String header) throws IOException {
        List<String> list = getFileHeaderInfo();
        for (String s : list) {
            writer.write(commentChar + s);
            writer.newLine();
        }
        if (header != null && header.length() > 0) {
            writer.write(commentChar + header);
            writer.newLine();
        }
    }

    public void writeBlock(BufferedWriter writer,
                           List<String> blockLine) throws IOException {
        if (blockLine != null && blockLine.size() > 0) {
            for (String line : blockLine) {
                writer.append(line);
                writer.newLine();
            }
            writer.flush();
        }
    }

    public void writeLine(BufferedWriter writer,
                          String line) throws IOException {
        if (line != null) {
            writer.append(line);
            writer.newLine();
            writer.flush();
        }
    }

    public void setExportType(int exportType) {
        this.exportType = exportType;
    }

    public int getExportType() {
        return exportType;
    }

    /**
     * May be overwrite this method to make block content description.
     * @return
     */
    public List<BlockContent> getBlockContent() {
        List<BlockContent> blocks = null;
        return blocks;
    }

    private List<BlockContent> getDefaultBlockContent() {
        List<BlockContent> blocks = new ArrayList<BlockContent>();

        BlockContent content = new BlockContent("DEFAULT");
        content.setSplitKey("");
        blocks.add(content);

        return blocks;
    }

    public void setDefaultBlockContent(boolean isDefault) {
        if (isDefault) {
            this.blockContents = this.getDefaultBlockContent();
        } else {
            this.blockContents = getBlockContent();
        }
    }

    public List<BlockContent> getCurrentBlockContents() {
        return this.blockContents;
    }

    public String getColumnNames(BlockContent content, List<String> data) {
        String columnNames = "";
        if (data != null && data.size() > 0) {
            columnNames = content.getHeaderExts() + separator + data.get(0);
        }
        return columnNames;
    }

    private String getFileName(String exts, String fileName) {
        String name = "";
        if (exts != null && exts.trim().length() > 0) {
            name = exts + "_" + fileName;
        } else {
            name = fileName;
        }
        return name;
    }

    public boolean isExportByBlock() {
        return this.exportType == EXPORT_BY_BLOCK;
    }

    public boolean isExportByLine() {
        return this.exportType == EXPORT_BY_LINE;
    }

    public boolean isSplitByEmptyline() {
        return this.splitType == SPLIT_BY_EMPTYLINE;
    }

    public boolean isSplitByKeyWord() {
        return this.splitType == SPLIT_BY_KEYWORD;
    }

    private boolean containKey(String key) {
        if (key != null && key.length() > 0) {
            for (BlockContent content : blockContents) {
                if (content.getSplitKey().equalsIgnoreCase(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setStartLinePos(int startLinePos) {
        this.startLinePos = startLinePos;
    }

    public int getStartLinePos() {
        return startLinePos;
    }

    private void updateRecordFlag() throws SQLException {
        String sqlCommand = "update C_RAW_FILES_MLMN set CONVERT_FLAG = 0, \n" +
            "IMPORT_FLAG = 1 where FILE_ID = " + fileId;
        executeSQLCommand(sqlCommand);
    }

    public void setSplitType(int splitType) {
        this.splitType = splitType;
    }

    public int getSplitType() {
        return splitType;
    }

    public void setCopyOfOriginal(boolean isCopy) {
        this.isCopy = isCopy;
    }

    public boolean isCopyOfOriginal() {
        return this.isCopy;
    }

    protected class BlockContent {
        private String name = "";
        private String splitKey = "";
        private String preFix = "";
        private String rawTable = "";
        private String headerExts = "";
        private String contentExts = "";
        private int blockIndex = -1;

        BlockContent(String name) {
            if (name != null && name.trim().length() > 0) {
                this.name = name;
            }
            this.blockIndex = 0;
        }

        BlockContent(String splitKey, String preFix, String rawTable,
                     String headerExts, String contentExts, int blockIndex) {
            this.splitKey = splitKey;
            this.preFix = preFix;
            this.rawTable = rawTable;
            this.headerExts = headerExts;
            this.contentExts = contentExts;
            this.blockIndex = blockIndex;
        }

        BlockContent(String splitKey, String preFix, String rawTable,
                     String headerExts, String contentExts) {
            this(splitKey, preFix, rawTable, headerExts, contentExts, 0);
        }

        public void setPreFix(String preFix) {
            this.preFix = preFix;
        }

        public String getPreFix() {
            return preFix;
        }

        public void setSplitKey(String splitKey) {
            this.splitKey = splitKey;
        }

        public String getSplitKey() {
            return splitKey;
        }

        public void setRawTable(String rawTable) {
            this.rawTable = rawTable;
        }

        public String getRawTable() {
            return rawTable;
        }

        public void setBlockIndex(int blockIndex) {
            this.blockIndex = blockIndex;
        }

        public int getBlockIndex() {
            return blockIndex;
        }

        public void setHeaderExts(String headerExts) {
            this.headerExts = headerExts;
        }

        public String getHeaderExts() {
            return headerExts;
        }

        public void setContentExts(String contentExts) {
            this.contentExts = contentExts;
        }

        public String getContentExts() {
            return contentExts;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

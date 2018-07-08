package vn.com.vhc.sts.cni.converter;

import java.util.ArrayList;
import java.util.List;


public class AlcatelRT110Converter extends SplitFileConverter {

    private String headerExts = "";
    private String contentExts = "";

    public AlcatelRT110Converter() {

    }

    public void init() {
        headerExts = "TIME" + separator;
        contentExts = getTime() + separator;

        setExportType(EXPORT_BY_BLOCK);
        setSplitType(SPLIT_BY_EMPTYLINE);
        setCopyOfOriginal(false);
        setStartLinePos(8);
        setDefaultBlockContent(false);
    }

    private String getTime() {
        return "20091220";
    }

    public List<BlockContent> getBlockContent() {
        List<BlockContent> blocks = new ArrayList<BlockContent>();
        BlockContent content = null;
        //
        content = new BlockContent("BLOCK_1");
        content.setRawTable("R_A_TEST");
        content.setHeaderExts(headerExts);
        content.setContentExts(contentExts);
        content.setBlockIndex(0);
        blocks.add(content);
        //

        return blocks;
    }
}

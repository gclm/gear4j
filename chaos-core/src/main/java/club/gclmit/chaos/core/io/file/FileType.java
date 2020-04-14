package club.gclmit.chaos.core.io.file;


import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 常见文件类型的魔数枚举
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/6 23:19
 * @version: V1.0
 */
public enum  FileType {

    /**
     * JPEG
     */
    JPEG("JPEG", "FFD8FF",MimeType.JPEG.getMimeType()),

    /**
     * JPG
     */
    JPG("JPG", "FFD8FF",MimeType.JPG.getMimeType()),

    /**
     * PNG
     */
    PNG("PNG", "89504E47",MimeType.PNG.getMimeType()),

    /**
     * GIF
     */
    GIF("GIF", "47494638",MimeType.GIF.getMimeType()),

    /**
     * TIFF
     */
    TIFF("TIFF", "49492A00",MimeType.TIFF.getMimeType()),

    /**
     * Windows bitmap
     */
    BMP("BMP", "424D",MimeType.BMP.getMimeType()),

    /**
     * CAD
     */
    DWG("DWG", "41433130",""),

    /**
     * Adobe photoshop
     */
    PSD("PSD", "38425053",""),

    /**
     * Rich Text Format
     */
    RTF("RTF", "7B5C727466",MimeType.RTF.getMimeType()),

    /**
     * XML
     */
    XML("XML", "3C3F786D6C",MimeType.XML.getMimeType()),

    /**
     * HTML
     */
    HTML("HTML", "68746D6C3E",MimeType.HTML.getMimeType()),
    /**
     *
     */
    HTM("HTM", "68746D6C3E",MimeType.HTM.getMimeType()),

    /**
     * Outlook Express
     */
    DBX("DBX", "CFAD12FEC5FD746F",""),

    /**
     * Outlook
     */
    PST("PST", "2142444E",""),

    /**
     * doc;xls;dot;ppt;xla;ppa;pps;pot;msi;sdw;db
     */
    OLE2("OLE2", "0xD0CF11E0A1B11AE1",MimeType.DOC.getMimeType()),

    /**
     * Microsoft Word/Excel
     */
    XLS_DOC("XLS_DOC", "D0CF11E0",MimeType.DOCX.getMimeType()),

    /**
     * Microsoft Access
     */
    MDB("MDB", "5374616E64617264204A",""),

    /**
     * Word Perfect
     */
    WPB("WPB", "FF575043",""),

    /**
     * Postscript
     */
    EPS_PS("EPS_PS", "252150532D41646F6265",""),

    /**
     * Adobe Acrobat
     */
    PDF("PDF", "255044462D312E",MimeType.PDF.getMimeType()),

    /**
     * Windows Password
     */
    PWL("PWL", "E3828596",""),

    /**
     * ZIP Archive
     */
    ZIP("ZIP", "504B0304",MimeType.ZIP.getMimeType()),

    /**
     * ARAR Archive
     */
    RAR("RAR", "52617221",MimeType.RAR.getMimeType()),

    /**
     * WAVE
     */
    WAV("WAV", "57415645",MimeType.WAV.getMimeType()),

    /**
     * AVI
     */
    AVI("AVI", "41564920",MimeType.AVI.getMimeType()),

    /**
     * Real Audio
     */
    RAM("RAM", "2E7261FD",""),

    /**
     * Real Media
     */
    RM("RM", "2E524D46",""),

    /**
     * Quicktime
     */
    MOV("MOV", "6D6F6F76",""),

    /**
     * Windows Media
     */
    ASF("ASF", "3026B2758E66CF11",""),

    /**
     * MIDI
     */
    MID("MID", "4D546864",MimeType.MID.getMimeType());

    /**
     * key
     */
    private String key;

    /**
     * 魔术
     */
    private String magicNumber;
    /**
     * mime 类型
     */
    private String mimeType;


    FileType(String key, String magicNumber, String mimeType) {
        this.key = key;
        this.magicNumber = magicNumber;
        this.mimeType = mimeType;
    }

    public String getKey() {
        return key;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public String getMimeType() {
        return mimeType;
    }

    /**
     *  基于魔数获取文件 mime
     *
     * @author gclm
     * @param: magicNumber
     * @date 2020/3/2 2:36 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getMimeType(String fileHeader){
        FileType[] fileTypes = values();
        for (FileType fileType : fileTypes){
            if (fileHeader.startsWith(fileType.getMagicNumber())) {
                String mimeType = fileType.getMimeType();
                if (StringUtils.isEmpty(mimeType)) {
                    return MimeType.DEFAULT_FILE_CONTENT_TYPE;
                }
                return mimeType;
            }
        }
        return MimeType.DEFAULT_FILE_CONTENT_TYPE;
    }

    /**
     *  基于魔数效验文件类型
     *
     * @author gclm
     * @param: fileHeader
     * @date 2020/3/2 2:50 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getKey(String fileHeader){
        FileType[] fileTypes = values();
        for (FileType fileType : fileTypes){
            if (fileHeader.startsWith(fileType.getMagicNumber())) {
                return fileType.getKey();
            }
        }
        return null;
    }
}

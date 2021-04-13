package club.gclmit.chaos.core.io;


import club.gclmit.chaos.core.utils.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 常见文件类型的魔数枚举
 * </p>
 *
 * @author gclm
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum MagicType {

    /**
     * JPEG
     */
    JPEG("jpeg", "FFD8FF",MimeType.JPEG.getMimeType()),

    /**
     * JPG
     */
    JPG("jpg", "FFD8FF",MimeType.JPG.getMimeType()),

    /**
     * PNG
     */
    PNG("png", "89504E47",MimeType.PNG.getMimeType()),

    /**
     * GIF
     */
    GIF("gif", "47494638",MimeType.GIF.getMimeType()),

    /**
     * TIFF
     */
    TIFF("tiff", "49492A00",MimeType.TIFF.getMimeType()),

    /**
     * Windows bitmap
     */
    BMP("bmp", "424D",MimeType.BMP.getMimeType()),

    /**
     * CAD
     */
    DWG("dwg", "41433130",""),

    /**
     * Adobe photoshop
     */
    PSD("psd", "38425053",""),

    /**
     * Rich Text Format
     */
    RTF("rtf", "7B5C727466",MimeType.RTF.getMimeType()),

    /**
     * XML
     */
    XML("xml", "3C3F786D6C",MimeType.XML.getMimeType()),

    /**
     * HTML
     */
    HTML("html", "68746D6C3E",MimeType.HTML.getMimeType()),
    /**
     *
     */
    HTM("htm", "68746D6C3E",MimeType.HTM.getMimeType()),

    /**
     * Outlook Express
     */
    DBX("dbx", "CFAD12FEC5FD746F",""),

    /**
     * Outlook
     */
    PST("pst", "2142444E",""),

    /**
     * doc;xls;dot;ppt;xla;ppa;pps;pot;msi;sdw;db
     */
    OLE2("ole2", "0xD0CF11E0A1B11AE1",MimeType.DOC.getMimeType()),

    /**
     * Microsoft Word/Excel
     */
    XLS_DOC("xls_doc", "D0CF11E0",MimeType.DOCX.getMimeType()),

    /**
     * Microsoft Access
     */
    MDB("mdb", "5374616E64617264204A",""),

    /**
     * Word Perfect
     */
    WPB("wpb", "FF575043",""),

    /**
     * Postscript
     */
    EPS_PS("EPS_PS", "252150532D41646F6265",""),

    /**
     * Adobe Acrobat
     */
    PDF("pdf", "255044462D312E",MimeType.PDF.getMimeType()),

    /**
     * Windows Password
     */
    PWL("pwl", "E3828596",""),

    /**
     * ZIP Archive
     */
    ZIP("zip", "504B0304",MimeType.ZIP.getMimeType()),

    /**
     * ARAR Archive
     */
    RAR("rar", "52617221",MimeType.RAR.getMimeType()),

    /**
     * WAVE
     */
    WAV("wav", "57415645",MimeType.WAV.getMimeType()),

    /**
     * AVI
     */
    AVI("avi", "41564920",MimeType.AVI.getMimeType()),

    /**
     * Real Audio
     */
    RAM("ram", "2E7261FD",""),

    /**
     * Real Media
     */
    RM("rm", "2E524D46",""),

    /**
     * Quicktime
     */
    MOV("mov", "6D6F6F76",""),

    /**
     * Windows Media
     */
    ASF("asf", "3026B2758E66CF11",""),

    /**
     * MIDI
     */
    MID("mid", "4D546864",MimeType.MID.getMimeType());

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 魔术
     */
    private String magicNumber;
    /**
     * mime 类型
     */
    private String mimeType;


    /**
     *  基于魔数获取文件 mime
     *
     * @author gclm
     * @param fileHeader  文件头
     * @return java.lang.String
     */
    public static String getMimeType(String fileHeader){
        MagicType[] magicTypes = values();
        for (MagicType magicType : magicTypes){
            if (fileHeader.startsWith(magicType.getMagicNumber())) {
                String mimeType = magicType.getMimeType();
                if (StringUtils.isEmpty(mimeType)) {
                    return MimeType.DEFAULT_FILE_CONTENT_TYPE;
                }
                return mimeType;
            }
        }
        return MimeType.DEFAULT_FILE_CONTENT_TYPE;
    }

    /**
     *  基于魔数效验文件后缀
     *
     * @author gclm
     * @param fileHeader  文件头
     * @return java.lang.String
     */
    public static String getSuffix(String fileHeader){
        MagicType[] magicTypes = values();
        for (MagicType magicType : magicTypes){
            if (fileHeader.startsWith(magicType.getMagicNumber())) {
                return magicType.getSuffix();
            }
        }
        return null;
    }
}

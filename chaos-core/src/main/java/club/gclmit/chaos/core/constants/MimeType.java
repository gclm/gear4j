package club.gclmit.chaos.core.constants;

/**
 * <p>
 * Mime type
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/2 1:13 下午
 * @version: V1.0
 * @since 1.8
 */
public enum  MimeType {

    AAC(".aac","AAC audio","audio/aac"),
    ABW(".abw","AbiWord document","application/x-abiword"),
    ARC(".arc","Archive document (multiple files embedded)","application/x-freearc"),
    AVI(".avi","AVI: Audio Video Interleave","video/x-msvideo"),
    AZW(".azw","Amazon Kindle eBook format","application/vnd.amazon.ebook"),
    BIN(".bin","Any kind of binary data","application/octet-stream"),
    BMP(".bmp","Windows OS/2 Bitmap Graphics","image/bmp"),
    BZ(".bz","BZip archive","application/x-bzip"),
    BZ2(".bz2","BZip2 archive","application/x-bzip2"),
    CSH(".csh","C-Shell script","application/x-csh"),
    CSS(".css","Cascading Style Sheets (CSS)","text/css"),
    CSV(".csv","Comma-separated values (CSV)","text/csv"),
    DOC(".doc","Microsoft Word","application/msword"),
    DOCX(".docx","Microsoft Word (OpenXML)","application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    EOT(".eot","MS Embedded OpenType fonts","application/vnd.ms-fontobject"),
    EPUB(".epub","Electronic publication (EPUB)","application/epub+zip"),
    GIF(".gif","Graphics Interchange Format (GIF)","image/gif"),
    HTM(".htm","HyperText Markup Language (HTML)","text/html"),
    HTML(".html","HyperText Markup Language (HTML)","text/html"),
    ICO(".ico","Icon format","image/vnd.microsoft.icon"),
    ICS(".ics","iCalendar format","text/calendar"),
    JAR(".jar","Java Archive (JAR)","application/java-archive"),
    JPG(".jpg","JPEG images","image/jpeg"),
    JPEG(".jpeg","JPEG images","image/jpeg"),
    JS(".js","JavaScript","text/javascript"),
    JSON(".json","JSON format","application/json"),
    JSONLD(".jsonld","JSON-LD format","application/ld+json"),
    MID(".mid","Musical Instrument Digital Interface (MIDI)","audio/midi audio/x-midi"),
    MIDI(".midi","Musical Instrument Digital Interface (MIDI)","audio/midi audio/x-midi"),
    MJS(".mjs","JavaScript module","text/javascript"),
    MP3(".mp3","MP3 audio","audio/mpeg"),
    MPEG(".mpeg","MPEG Video","video/mpeg"),
    MPKG(".mpkg","Apple Installer Package","application/vnd.apple.installer+xml"),
    ODP(".odp","OpenDocument presentation document","application/vnd.oasis.opendocument.presentation"),
    ODS(".ods","OpenDocument spreadsheet document","application/vnd.oasis.opendocument.spreadsheet"),
    ODT(".odt","OpenDocument text document","application/vnd.oasis.opendocument.text"),
    OGA(".oga","OGG audio","audio/ogg"),
    OGV(".ogv","OGG video","video/ogg"),
    OGX(".ogx","OGG","application/ogg"),
    OTF(".otf","OpenType font","font/otf"),
    PNG(".png","Portable Network Graphics","image/png"),
    PDF(".pdf","Adobe Portable Document Format (PDF)","application/pdf"),
    PPT(".ppt","Microsoft PowerPoint","application/vnd.ms-powerpoint"),
    PPTX(".pptx","Microsoft PowerPoint (OpenXML)","application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    RAR(".rar","RAR archive","application/x-rar-compressed"),
    RTF(".rtf","Rich Text Format (RTF)","application/rtf"),
    SH(".sh","Bourne shell script","application/x-sh"),
    SVG(".svg","Scalable Vector Graphics (SVG)","image/svg+xml"),
    SWF(".swf","Small web format (SWF) or Adobe Flash document","application/x-shockwave-flash"),
    TAR(".tar","Tape Archive (TAR)","application/x-tar"),
    TIF(".tif","Tagged Image File Format (TIFF)","image/tiff"),
    TIFF(".tiff","Tagged Image File Format (TIFF)","image/tiff"),
    TTF(".ttf","TrueType Font","font/ttf"),
    TXT(".txt","Text, (generally ASCII or ISO 8859-n)","text/plain"),
    VSD(".vsd","Microsoft Visio","application/vnd.visio"),
    WAV(".wav","Waveform Audio Format","audio/wav"),
    WEBA(".weba","WEBM audio","audio/webm"),
    WEBM(".webm","WEBM video","video/webm"),
    WEBP(".webp","WEBP image","image/webp"),
    WOFF(".woff","Web Open Font Format (WOFF)","font/woff"),
    WOFF2(".woff2","Web Open Font Format (WOFF)","font/woff2"),
    XHTML(".xhtml","XHTML","application/xhtml+xml"),
    XLS(".xls","Microsoft Excel","application/vnd.ms-excel"),
    XLSX(".xlsx","Microsoft Excel (OpenXML)","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XML(".xml","XML","text/xml"),
    XUL(".xul","XUL","application/vnd.mozilla.xul+xml"),
    ZIP(".zip","ZIP archive","application/zip"),
    V_3GP(".3gp","3GPP audio/video container","video/3gpp"),
    V_3G2(".3g2","3GPP2 audio/video container","video/3gpp2"),
    Z_7Z(".7z","7-zip archive","application/x-7z-compressed");

    public static final String DEFAULT_FILE_CONTENT_TYPE = "application/octet-stream";

    /**
     * 后缀
     */
    private String sufix;

    /**
     * 文档类型
     */
    private String docType;

    /**
     * mime
     */
    private String mimeType;


    MimeType(String sufix, String docType, String mimeType) {
        this.sufix = sufix;
        this.docType = docType;
        this.mimeType = mimeType;
    }

    public String getSufix() {
        return sufix;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getDocType() {
        return docType;
    }

    /**
     *  根据前缀获取文档的 Mime
     *
     * @author gclm
     * @param: sufix
     * @date 2020/3/2 1:47 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getMime(String sufix) {
        MimeType[] mimeTypes = values();
        for (MimeType mimeType : mimeTypes) {
            if (sufix.equals(mimeType.getSufix())) {
                return mimeType.getMimeType();
            }
        }
        return DEFAULT_FILE_CONTENT_TYPE;
    }
}

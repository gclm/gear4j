package club.gclmit.chaos.core.io;


/**
 * <p>
 * Mime type
 * </p>
 *
 * @author gclm
 */
public enum MimeType {

    /**
     * AAC audio
     */
    AAC(".aac", "AAC audio", "audio/aac"),
    /**
     * AbiWord document
     */
    ABW(".abw", "AbiWord document", "application/x-abiword"),
    /**
     * Archive document (multiple files embedded)
     */
    ARC(".arc", "Archive document (multiple files embedded)", "application/x-freearc"),
    /**
     * AVI: Audio Video Interleave
     */
    AVI(".avi", "AVI: Audio Video Interleave", "video/x-msvideo"),
    /**
     * Amazon Kindle eBook format
     */
    AZW(".azw", "Amazon Kindle eBook format", "application/vnd.amazon.ebook"),
    /**
     * Any kind of binary data
     */
    BIN(".bin", "Any kind of binary data", "application/octet-stream"),
    /**
     * Windows OS/2 Bitmap Graphics
     */
    BMP(".bmp", "Windows OS/2 Bitmap Graphics", "image/bmp"),
    /**
     * BZip archive
     */
    BZ(".bz", "BZip archive", "application/x-bzip"),
    /**
     * BZip2 archive
     */
    BZ2(".bz2", "BZip2 archive", "application/x-bzip2"),
    /**
     * C-Shell script
     */
    CSH(".csh", "C-Shell script", "application/x-csh"),
    /**
     * Cascading Style Sheets (CSS)
     */
    CSS(".css", "Cascading Style Sheets (CSS)", "text/css"),
    /**
     * Comma-separated values (CSV)
     */
    CSV(".csv", "Comma-separated values (CSV)", "text/csv"),
    /**
     * Microsoft Word
     */
    DOC(".doc", "Microsoft Word", "application/msword"),
    /**
     * Microsoft Word (OpenXML)
     */
    DOCX(".docx", "Microsoft Word (OpenXML)", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    /**
     * MS Embedded OpenType fonts
     */
    EOT(".eot", "MS Embedded OpenType fonts", "application/vnd.ms-fontobject"),
    /**
     * Electronic publication (EPUB)
     */
    EPUB(".epub", "Electronic publication (EPUB)", "application/epub+zip"),
    /**
     * Graphics Interchange Format (GIF)
     */
    GIF(".gif", "Graphics Interchange Format (GIF)", "image/gif"),
    /**
     * HyperText Markup Language (HTML)
     */
    HTM(".htm", "HyperText Markup Language (HTML)", "text/html"),
    /**
     * HyperText Markup Language (HTML)
     */
    HTML(".html", "HyperText Markup Language (HTML)", "text/html"),
    /**
     * Icon format
     */
    ICO(".ico", "Icon format", "image/vnd.microsoft.icon"),
    /**
     * iCalendar format
     */
    ICS(".ics", "iCalendar format", "text/calendar"),
    /**
     * Java Archive (JAR)
     */
    JAR(".jar", "Java Archive (JAR)", "application/java-archive"),
    /**
     * JPEG images
     */
    JPG(".jpg", "JPEG images", "image/jpeg"),
    /**
     * JPEG images
     */
    JPEG(".jpeg", "JPEG images", "image/jpeg"),
    /**
     * JavaScript
     */
    JS(".js", "JavaScript", "text/javascript"),
    /**
     * JSON format
     */
    JSON(".json", "JSON format", "application/json"),
    /**
     * JSON-LD format
     */
    JSONLD(".jsonld", "JSON-LD format", "application/ld+json"),
    /**
     * Musical Instrument Digital Interface (MIDI)
     */
    MID(".mid", "Musical Instrument Digital Interface (MIDI)", "audio/midi audio/x-midi"),
    /**
     * Musical Instrument Digital Interface (MIDI)
     */
    MIDI(".midi", "Musical Instrument Digital Interface (MIDI)", "audio/midi audio/x-midi"),
    /**
     * JavaScript module
     */
    MJS(".mjs", "JavaScript module", "text/javascript"),
    /**
     * MP3 audio
     */
    MP3(".mp3", "MP3 audio", "audio/mpeg"),
    /**
     * MPEG Video
     */
    MPEG(".mpeg", "MPEG Video", "video/mpeg"),
    /**
     * Apple Installer Package
     */
    MPKG(".mpkg", "Apple Installer Package", "application/vnd.apple.installer+xml"),
    /**
     * OpenDocument presentation document
     */
    ODP(".odp", "OpenDocument presentation document", "application/vnd.oasis.opendocument.presentation"),
    /**
     * OpenDocument spreadsheet document
     */
    ODS(".ods", "OpenDocument spreadsheet document", "application/vnd.oasis.opendocument.spreadsheet"),
    /**
     * OpenDocument text document
     */
    ODT(".odt", "OpenDocument text document", "application/vnd.oasis.opendocument.text"),
    /**
     * OGG audio
     */
    OGA(".oga", "OGG audio", "audio/ogg"),
    /**
     * OGG video
     */
    OGV(".ogv", "OGG video", "video/ogg"),
    /**
     * OGG
     */
    OGX(".ogx", "OGG", "application/ogg"),
    /**
     * OpenType font
     */
    OTF(".otf", "OpenType font", "font/otf"),
    /**
     * Portable Network Graphics
     */
    PNG(".png", "Portable Network Graphics", "image/png"),
    /**
     * Adobe Portable Document Format (PDF)
     */
    PDF(".pdf", "Adobe Portable Document Format (PDF)", "application/pdf"),
    /**
     * Microsoft PowerPoint
     */
    PPT(".ppt", "Microsoft PowerPoint", "application/vnd.ms-powerpoint"),
    /**
     * Microsoft PowerPoint (OpenXML)
     */
    PPTX(".pptx", "Microsoft PowerPoint (OpenXML)", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    /**
     * RAR archive
     */
    RAR(".rar", "RAR archive", "application/x-rar-compressed"),
    /**
     * Rich Text Format (RTF)
     */
    RTF(".rtf", "Rich Text Format (RTF)", "application/rtf"),
    /**
     * Bourne shell script
     */
    SH(".sh", "Bourne shell script", "application/x-sh"),
    /**
     * Scalable Vector Graphics (SVG)
     */
    SVG(".svg", "Scalable Vector Graphics (SVG)", "image/svg+xml"),
    /**
     * Small web format (SWF) or Adobe Flash document
     */
    SWF(".swf", "Small web format (SWF) or Adobe Flash document", "application/x-shockwave-flash"),
    /**
     * Tape Archive (TAR)
     */
    TAR(".tar", "Tape Archive (TAR)", "application/x-tar"),
    /**
     * Tagged Image File Format (TIFF)
     */
    TIF(".tif", "Tagged Image File Format (TIFF)", "image/tiff"),
    /**
     * Tagged Image File Format (TIFF)
     */
    TIFF(".tiff", "Tagged Image File Format (TIFF)", "image/tiff"),
    /**
     * TrueType Font
     */
    TTF(".ttf", "TrueType Font", "font/ttf"),
    /**
     * Text, (generally ASCII or ISO 8859-n)
     */
    TXT(".txt", "Text, (generally ASCII or ISO 8859-n)", "text/plain"),
    /**
     * Microsoft Visio
     */
    VSD(".vsd", "Microsoft Visio", "application/vnd.visio"),
    /**
     * Waveform Audio Format
     */
    WAV(".wav", "Waveform Audio Format", "audio/wav"),
    /**
     * WEBM audio
     */
    WEBA(".weba", "WEBM audio", "audio/webm"),
    /**
     * WEBM video
     */
    WEBM(".webm", "WEBM video", "video/webm"),
    /**
     * WEBP image
     */
    WEBP(".webp", "WEBP image", "image/webp"),
    /**
     * Web Open Font Format (WOFF)
     */
    WOFF(".woff", "Web Open Font Format (WOFF)", "font/woff"),
    /**
     * Web Open Font Format (WOFF)
     */
    WOFF2(".woff2", "Web Open Font Format (WOFF)", "font/woff2"),
    /**
     * XHTML
     */
    XHTML(".xhtml", "XHTML", "application/xhtml+xml"),
    /**
     * Microsoft Excel
     */
    XLS(".xls", "Microsoft Excel", "application/vnd.ms-excel"),
    /**
     * Microsoft Excel (OpenXML)
     */
    XLSX(".xlsx", "Microsoft Excel (OpenXML)", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    /**
     * XML
     */
    XML(".xml", "XML", "text/xml"),
    /**
     * XUL
     */
    XUL(".xul", "XUL", "application/vnd.mozilla.xul+xml"),
    /**
     * ZIP archive
     */
    ZIP(".zip", "ZIP archive", "application/zip"),
    /**
     * 3GPP audio/video container
     */
    V_3GP(".3gp", "3GPP audio/video container", "video/3gpp"),
    /**
     * 3GPP2 audio/video container
     */
    V_3G2(".3g2", "3GPP2 audio/video container", "video/3gpp2"),
    /**
     * 7-zip archive
     */
    Z_7Z(".7z", "7-zip archive", "application/x-7z-compressed");


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

    /**
     * 根据前缀获取文档的 Mime
     *
     * @author gclm
     * @param suffix 文件后缀
     * @return java.lang.String
     */
    public static String getMime(String suffix) {
        MimeType[] mimeTypes = values();
        for (MimeType mimeType : mimeTypes) {
            if (suffix.equals(mimeType.getSufix())) {
                return mimeType.getMimeType();
            }
        }
        return DEFAULT_FILE_CONTENT_TYPE;
    }

    /**
     * <p>
     * 生成枚举注释
     * </p>
     *
     * @author gclm
     * @param type MimeType
     * @return java.lang.String
     */
    public String generate(MimeType type) {
        String template = " /**\n" +
                "     * %s\n" +
                "     */\n" +
                "    %s(\"%s\",\"%s\",\"%s\"),";
        return String.format(template, type.getDocType(), type.name(), type.getSufix(), type.getDocType(), type.getMimeType());
    }

    public static void main(String[] args) {
        MimeType[] mimeTypes = values();
        for (MimeType mimeType : mimeTypes) {
            System.out.println(mimeType.generate(mimeType));
        }
    }

    public String getSufix() {
        return sufix;
    }

    public String getDocType() {
        return docType;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String toString() {
        return "MimeType{" +
                "sufix='" + sufix + '\'' +
                ", docType='" + docType + '\'' +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }
}

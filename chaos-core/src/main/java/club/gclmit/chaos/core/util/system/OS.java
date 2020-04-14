package club.gclmit.chaos.core.util.system;

/**
 * <p>
 * 系统平台
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/14 8:12 下午
 * @version: V1.0
 * @since 1.8
 */
public enum  OS {

    Any("any"),
    Linux("Linux"),
    Mac_OS("Mac OS"),
    Mac_OS_X("Mac OS X"),
    Windows("Windows"),
    OS2("OS/2"),
    Solaris("Solaris"),
    SunOS("SunOS"),
    MPEiX("MPE/iX"),
    HP_UX("HP-UX"),
    AIX("AIX"),
    OS390("OS/390"),
    FreeBSD("FreeBSD"),
    Irix("Irix"),
    Digital_Unix("Digital Unix"),
    NetWare_411("NetWare"),
    OSF1("OSF1"),
    OpenVMS("OpenVMS"),
    Others("Others");

    private String description;

    private OS(String desc){
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "OS{" +
                "description='" + description + '\'' +
                '}';
    }
}

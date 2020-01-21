package club.gclmit.chaos.security.core.social.qq.api;

/**
 * <p>
 * QQ 获取信息实体类
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/20 7:36 下午
 * @version: V1.0
 * @since 1.8
 */
public class QQUserInfo {

    /**
     * 2020年1月7日 返回参数
     * {
     *     "ret": 0,
     *     "msg": "",
     *     "is_lost":0,
     *     "nickname": "孤城落寞",
     *     "gender": "男",
     *     "gender_type": 1,
     *     "province": "上海",
     *     "city": "浦东新区",
     *     "year": "1996",
     *     "constellation": "",
     *     "figureurl": "http:\/\/qzapp.qlogo.cn\/qzapp\/101834415\/5D146012526F5B59BA95B438B91716C9\/30",
     *     "figureurl_1": "http:\/\/qzapp.qlogo.cn\/qzapp\/101834415\/5D146012526F5B59BA95B438B91716C9\/50",
     *     "figureurl_2": "http:\/\/qzapp.qlogo.cn\/qzapp\/101834415\/5D146012526F5B59BA95B438B91716C9\/100",
     *     "figureurl_qq_1": "http://thirdqq.qlogo.cn/g?b=oidb&k=jq4ygcP1aezA8jWBvzrNxQ&s=40&t=1574255425",
     *     "figureurl_qq_2": "http://thirdqq.qlogo.cn/g?b=oidb&k=jq4ygcP1aezA8jWBvzrNxQ&s=100&t=1574255425",
     *     "figureurl_qq": "http://thirdqq.qlogo.cn/g?b=oidb&k=jq4ygcP1aezA8jWBvzrNxQ&s=640&t=1574255425",
     *     "figureurl_type": "1",
     *     "is_yellow_vip": "0",
     *     "vip": "0",
     *     "yellow_vip_level": "0",
     *     "level": "0",
     *     "is_yellow_year_vip": "0"
     * }
     */

    /**
     * 文档上没有，但是实际上返回参数
     */
    private Integer gender_type;
    private Integer is_lost;
    private String  constellation;
    private String  figureurl_qq;
    private String  figureurl_type;

    /**
     * 	返回码
     */
    private String ret;
    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;
    /**
     *
     */
    private String openId;

    /**
     * 省(直辖市)
     */
    private String province;
    /**
     * 市(直辖市区)
     */
    private String city;
    /**
     * 出生年月
     */
    private String year;
    /**
     * 	用户在QQ空间的昵称。
     */
    private String nickname;
    /**
     * 	大小为30×30像素的QQ空间头像URL。
     */
    private String figureurl;
    /**
     * 	大小为50×50像素的QQ空间头像URL。
     */
    private String figureurl_1;
    /**
     * 	大小为100×100像素的QQ空间头像URL。
     */
    private String figureurl_2;
    /**
     * 	大小为40×40像素的QQ头像URL。
     */
    private String figureurl_qq_1;
    /**
     * 	大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
     */
    private String figureurl_qq_2;
    /**
     * 	性别。 如果获取不到则默认返回”男”
     */
    private String gender;
    /**
     * 	标识用户是否为黄钻用户（0：不是；1：是）。
     */
    private String is_yellow_vip;
    /**
     * 	标识用户是否为黄钻用户（0：不是；1：是）
     */
    private String vip;
    /**
     * 	黄钻等级
     */
    private String yellow_vip_level;
    /**
     * 	黄钻等级
     */
    private String level;
    /**
     * 标识是否为年费黄钻用户（0：不是； 1：是）
     */
    private String is_yellow_year_vip;


    public String getFigureurl_type() {
        return figureurl_type;
    }

    public void setFigureurl_type(String figureurl_type) {
        this.figureurl_type = figureurl_type;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl_1() {
        return figureurl_1;
    }

    public void setFigureurl_1(String figureurl_1) {
        this.figureurl_1 = figureurl_1;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public void setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIs_yellow_vip() {
        return is_yellow_vip;
    }

    public void setIs_yellow_vip(String is_yellow_vip) {
        this.is_yellow_vip = is_yellow_vip;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getYellow_vip_level() {
        return yellow_vip_level;
    }

    public void setYellow_vip_level(String yellow_vip_level) {
        this.yellow_vip_level = yellow_vip_level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIs_yellow_year_vip() {
        return is_yellow_year_vip;
    }

    public void setIs_yellow_year_vip(String is_yellow_year_vip) {
        this.is_yellow_year_vip = is_yellow_year_vip;
    }

    public Integer getIs_lost() {
        return is_lost;
    }

    public void setIs_lost(Integer is_lost) {
        this.is_lost = is_lost;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public Integer getGender_type() {
        return gender_type;
    }

    public void setGender_type(Integer gender_type) {
        this.gender_type = gender_type;
    }

    public String getFigureurl_qq() {
        return figureurl_qq;
    }

    public void setFigureurl_qq(String figureurl_qq) {
        this.figureurl_qq = figureurl_qq;
    }
}


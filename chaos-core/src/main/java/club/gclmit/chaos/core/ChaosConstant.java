package club.gclmit.chaos.core;

import java.nio.charset.Charset;

/**
 * <p>
 *  项目常量
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/13 10:02 上午
 * @version: V1.0
 * @since 1.8
 */
public interface ChaosConstant {
    /**
     * 空串
     */
    String	BLANK					= "";
    /**
     * 反斜扛
     */
    byte	BACKSLASH				= '/';
    /**
     * 斜扛
     */
    byte	SLASH					= '\\';
    /**
     * \r
     */
    byte	CR						= 13;
    /**
     * \n
     */
    byte	LF						= 10;
    /**
     * =
     */
    byte	EQ						= '=';
    /**
     * =
     */
    String	STR_EQ					= "=";
    /**
     * &
     */
    byte	AMP						= '&';
    /**
     * &
     */
    String	STR_AMP					= "&";
    /**
     * :
     */
    byte	COL						= ':';
    /**
     * :
     */
    String	STR_COL					= ":";
    /**
     * ;
     */
    byte	SEMI_COL				= ';';
    /**
     * 一个空格
     */
    byte	SPACE					= ' ';
    /**
     * 左括号
     */
    byte	LEFT_BRACKET			= '(';
    /**
     * 右括号
     */
    byte	RIGHT_BRACKET			= ')';
    /**
     * ?
     */
    byte	ASTERISK				= '?';
    byte[]	CR_LF_CR_LF				= { CR, LF, CR, LF };
    byte[]	CR_LF					= { CR, LF };
    byte[]	LF_LF					= { LF, LF };
    byte[]	SPACE_					= { SPACE };
    byte[]	CR_						= { CR };
    byte[]	LF_						= { LF };
    byte[]	NULL					= { 'n', 'u', 'l', 'l' };

    /**
     * \r\n
     */
    String	CRLF					= "\r\n";
    String	DEFAULT_ENCODING		= "utf-8";

    Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODING);
}

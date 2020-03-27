package club.gclmit.chaos.core;

import club.gclmit.chaos.core.helper.TimeHelper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * <p>
 *  时间工具测试工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/27 10:35 上午
 * @version: V1.0
 * @since 1.8
 */
public class TimeTests {

    public static void main(String[] args) {
        long timestamp = 1585275097596L;
        LocalDateTime dateTime = LocalDateTime.now();
        getWeekName(timestamp,dateTime);
    }

    public static void getWeekName(long timestamp,LocalDateTime dateTime){
        System.out.println(TimeHelper.parseTimestampToWeekName(timestamp));
        System.out.println("=========================================");
        System.out.println(TimeHelper.parseDateTimeToWeekName(LocalDateTime.now()));
        System.out.println(TimeHelper.parseDateTimeToWeekName(LocalDateTime.now().plusDays(1L)));
        System.out.println("=========================================");

        long second = dateTime.toEpochSecond(ZoneOffset.of("+8"));
        long second2 = dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long milli  =  dateTime.atZone(ZoneId.of("+8")).toInstant().toEpochMilli();
        System.out.println("second："+ second +"\nsecond2："+ second2 +"\nmilli：" + milli);
        System.out.println("=========================================");
        System.out.println(TimeHelper.parseTimestampToWeekName(second));
        System.out.println("-----------------------------------------");
        System.out.println(TimeHelper.parseTimestampToWeekName(second2));
        System.out.println("-----------------------------------------");
        System.out.println(TimeHelper.parseTimestampToWeekName(milli));
        System.out.println(TimeHelper.parseTimestampToWeekName(158527486L));
    }
}


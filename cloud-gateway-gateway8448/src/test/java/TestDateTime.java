import java.time.ZonedDateTime;

/**
 * @author GZH
 * @date 2022-03-11
 */
public class TestDateTime {
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        //2022-03-11T09:33:32.348+08:00[Asia/Shanghai]
    }
}

import com.gebilaoyi.j2ee.snowflake.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongUnaryOperator;
import java.util.stream.IntStream;

@Slf4j
public class SnowflakeIdUtilTest {
    private AtomicLong sequence = new AtomicLong(0L);

    @Test
    public void test2() {
        LongUnaryOperator l = (x)-> x + 1 & 2048;
        //sequence.incrementAndGet() & sequenceMask
        sequence.updateAndGet(l) ;
        System.out.println(sequence.incrementAndGet());
    }

    @Test
    public void test() throws Exception {
        int length = 500;
        Set<Long> set = Collections.synchronizedSet(new HashSet<>(length));
        List<Long> list = Collections.synchronizedList(new ArrayList<>(length));

        int[] element = new int[length];
        for (int i = 0; i < length; i++) {
            element[i] = i;
        }
        IntStream intParallelStream = Arrays.stream(element).parallel();
        intParallelStream.forEach(s ->
        {
            Long id = SnowflakeIdUtil.nextId();
            set.add(id);
            list.add(id);
        });
        log.info("set元素数量 ：" + set.size());
        log.info("List元素数量 ：" + list.size());

        Set<Long> set2 = new HashSet<>();
        for (Long a : list) {
            log.info("element is:{}", a);
            set2.add(a);
        }
        log.info("List碰撞元素数量 ：" + set2.size());
        if(set2.size() < list.size()) {
            throw new Exception("snowflake generate error.");
        }
    }

}

package zfar.org.java.shoot.comparator;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * main: Comparator interface
 * others: ThreadLocalRandom、LocalDateTime and some lombok annotations
 * @author zfar
 */
public class OrderSort {

    // 核心数据结构
    @Builder
    @Getter
    @ToString
    public static class Order {
        private String orderId;
        private String userId;
        private List<OrderItem> items;
        private BigDecimal totalAmount; // 缓存总金额
        private LocalDateTime createTime;
    }

    @Setter
    @Getter
    public static class OrderItem {
        private String productId;
        private BigDecimal price;
        private int quantity;
    }

    // 策略模式
    public interface SortStrategy {
        Comparator<Order> getComparator();
    }

    // 金额优先策略
    public static class AmountFirstStrategy implements SortStrategy {
        @Override
        public Comparator<Order> getComparator() {
            return (o1, o2) -> {
                int cmp = o2.getTotalAmount().compareTo(o1.getTotalAmount());
                return cmp != 0 ? cmp : o1.getCreateTime().compareTo(o2.getCreateTime());
            };
        }
    }

    // 时间优先策略
    public static class TimeFirstStrategy implements SortStrategy {
        @Override
        public Comparator<Order> getComparator() {
            return Comparator.comparing(Order::getCreateTime)
                    .thenComparing(Order::getTotalAmount, Comparator.reverseOrder());
        }
    }

    public static void main(String[] args) {
        List<Order> orders = getOrders(5);
        System.out.println("Before sorted:");
        orders.forEach(System.out::println);

        SortStrategy strategy = new AmountFirstStrategy();
        orders.sort(strategy.getComparator());
        System.out.println("After sorted by Price:");
        orders.forEach(System.out::println);

        strategy = new TimeFirstStrategy();
        orders.sort(strategy.getComparator());
        System.out.println("After sorted by Time:");
        orders.forEach(System.out::println);
    }

    public static List<Order> getOrders(int number) {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

        List<Order> ret = Lists.newArrayList();
        while (number-- > 0) {
            ret.add(
                Order.builder()
                    .orderId(String.valueOf(number))
                    .totalAmount(BigDecimal.valueOf(
                        ThreadLocalRandom.current().nextDouble()
                    ))
                    .createTime(generateRandomDateTime(start, end)).build()
            );
        }
        return ret;
    }

    public static LocalDateTime generateRandomDateTime(LocalDateTime start, LocalDateTime end) {
        // 计算时间范围内的总秒数
        long seconds = ChronoUnit.SECONDS.between(start, end);

        // 生成随机秒数偏移量
        long randomSeconds = ThreadLocalRandom.current().nextLong(seconds + 1);

        // 返回随机时间
        return start.plusSeconds(randomSeconds);
    }
}

package cn.herodotus.dante.athena.autoconfigure.processor;

import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * <p>Description: AthenaBusBridge </p>
 * <p>
 * 解决单体版对 Spring Cloud Stream 的依赖，导致必须连接访问 Kafka 问题。
 *
 * @author : gengwei.zheng
 * @date : 2022/4/8 22:51
 */
@Component
public class AthenaBusBridge implements BusBridge {

    @Override
    public void send(RemoteApplicationEvent event) {

    }
}

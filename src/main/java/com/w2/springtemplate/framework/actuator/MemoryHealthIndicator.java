package com.w2.springtemplate.framework.actuator;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator extends AbstractHealthIndicator {

    private final MeterRegistry meterRegistry;
    private final double heapThreshold;

    public MemoryHealthIndicator(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.heapThreshold = 80;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
// 获取所有堆内存区域的已使用内存总和
        double usedHeap = meterRegistry.find("jvm.memory.used")
                .tag("area", "heap")
                .gauges().stream()
                .mapToDouble(Gauge::value)
                .sum();

        // 获取所有堆内存区域的最大内存总和
        double maxHeap = meterRegistry.find("jvm.memory.max")
                .tag("area", "heap")
                .gauges().stream()
                .mapToDouble(Gauge::value)
                .sum();
        double usagePercent = (usedHeap / maxHeap) * 100;
        boolean isHealthy = usagePercent <= heapThreshold;
        if (isHealthy) {
            builder.up()
                    .withDetail("usage", String.format("%.2f%%", usagePercent))
                    .withDetail("threshold", String.format("%.2f%%", heapThreshold));
        } else {
            builder.down()
                    .withDetail("error", "堆内存使用率超过阈值")
                    .withDetail("usage", String.format("%.2f%%", usagePercent))
                    .withDetail("threshold", String.format("%.2f%%", heapThreshold));
        }
    }
}

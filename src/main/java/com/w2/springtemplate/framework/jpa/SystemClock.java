package com.w2.springtemplate.framework.jpa;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 增强版 SystemClock（继承自 Clock） - 支持单例模式 - 继承 Clock 以兼容 Java 时间 API - 保留原有高并发优化特性
 */
public final class SystemClock extends Clock {
	// 单例实例（默认使用 UTC 时区）
	private static final SystemClock INSTANCE = new SystemClock(ZoneId.of("UTC"), 1);

	private final ZoneId zone;
	private final long period;
	private volatile long now;
	private final ScheduledExecutorService scheduler;
	private final AtomicBoolean running = new AtomicBoolean(true);

	// 私有构造方法（保证单例）
	private SystemClock(ZoneId zone, long period) {
		this.zone = zone;
		this.period = period;
		this.now = computeCurrentTime();
		this.scheduler = Executors.newSingleThreadScheduledExecutor(this::createThreadFactory);
		startClockUpdating();
		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
	}

	// 获取单例实例
	public static SystemClock instance() {
		return INSTANCE;
	}

	// 获取指定时区实例（可选扩展）
	public static SystemClock of(ZoneId zone) {
		return new SystemClock(zone, 1);
	}

	// ----- Clock 抽象方法实现 -----
	@Override
	public ZoneId getZone() {
		return zone;
	}

	@Override
	public Clock withZone(ZoneId zone) {
		if (this.zone.equals(zone)) {
			return this;
		}
		return new SystemClock(zone, this.period); // 创建新实例
	}

	@Override
	public long millis() {
		return now;
	}

	@Override
	public Instant instant() {
		return Instant.ofEpochMilli(now);
	}

	// ----- 原有功能增强 -----
	private Thread createThreadFactory(Runnable r) {
		Thread thread = new Thread(r, "SystemClock-" + zone.getId());
		thread.setDaemon(true);
		thread.setUncaughtExceptionHandler((t, e) -> System.err.println("SystemClock thread error: " + e.getMessage()));
		return thread;
	}

	private void startClockUpdating() {
		scheduler.scheduleAtFixedRate(() -> {
			try {
				now = computeCurrentTime();
			} catch (Throwable t) {
				if (running.get()) {
					System.err.println("Update clock failed: " + t.getMessage());
				}
			}
		}, 0, period, TimeUnit.MILLISECONDS);
	}

	private long computeCurrentTime() {
		// 根据时区计算时间（示例逻辑，需按需完善）
		return System.currentTimeMillis() + zone.getRules().getOffset(Instant.now()).getTotalSeconds() * 1000L;
	}

	private void shutdown() {
		running.set(false);
		scheduler.shutdown();
		try {
			if (!scheduler.awaitTermination(100, TimeUnit.MILLISECONDS)) {
				scheduler.shutdownNow();
			}
		} catch (InterruptedException e) {
			scheduler.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	// ----- 兼容旧版 API -----
	public static long now() {
		return INSTANCE.now;
	}

	public static long currentTimeMillis() {
		return now();
	}
}

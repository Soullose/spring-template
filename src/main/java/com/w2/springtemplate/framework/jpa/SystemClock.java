package com.w2.springtemplate.framework.jpa;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 系统时钟优化版（JDK 11+）
 * 1. 使用枚举实现防反射单例
 * 2. 添加优雅关闭逻辑
 * 3. 增强异常处理
 * 4. 内存可见性保障
 */
public enum SystemClock {

    INSTANCE(1); // 默认1ms更新周期

    private final long period;
    private volatile long now;
    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean running = new AtomicBoolean(true);

    // 定义 VarHandle 用于原子操作
    private static final VarHandle NOW_HANDLE;
    static {
        try {
            // 获取 SystemClock 类中名为 "now" 的 long 类型字段的 VarHandle
            NOW_HANDLE = MethodHandles.privateLookupIn(SystemClock.class, MethodHandles.lookup())
                    .findVarHandle(SystemClock.class, "now", long.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError("Failed to initialize VarHandle for 'now'"+ e);
        }
    }
    /**
     * 枚举构造函数（线程安全）
     */
    SystemClock(long period) {
        this.period = period;
        this.now = System.currentTimeMillis();

//        setNow(System.currentTimeMillis());

        this.scheduler = Executors.newSingleThreadScheduledExecutor(this::createThreadFactory);

        // 启动时钟更新任务
        startClockUpdating();

        // 注册JVM关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

//    // 通过 VarHandle 原子写入
//    private void setNow(long newValue) {
//        NOW_HANDLE.setVolatile(this, newValue);
//    }
//
//    // 通过 VarHandle 原子读取
//    private long getNow() {
//        return (long) NOW_HANDLE.getVolatile(this);
//    }
    /**
     * 创建守护线程工厂
     */
    private Thread createThreadFactory(Runnable r) {
        var thread = new Thread(r, "SystemClock-Daemon");
        thread.setDaemon(true); // 防止阻塞JVM退出
        thread.setUncaughtExceptionHandler((t, e) ->
                System.err.println("SystemClock thread error: " + e.getMessage()));
        return thread;
    }

    /**
     * 启动时钟更新任务（含异常捕获）
     */
    private void startClockUpdating() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                now = System.currentTimeMillis();
//                setNow(System.currentTimeMillis());
            } catch (Throwable t) {
                if (running.get()) { // 仅运行态记录错误
                    System.err.println("Update clock failed: " + t.getMessage());
                }
            }
        }, 0, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 优雅关闭资源
     */
    private void shutdown() {
        running.set(false);
        scheduler.shutdown(); // 禁用新任务

        try {
            if (!scheduler.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                scheduler.shutdownNow(); // 强制终止
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 获取当前时间（对外暴露方法）
     */
    public static long now() {
        return INSTANCE.now;
//        return INSTANCE.getNow();
    }

    //----- 兼容旧版API -----
    public static long currentTimeMillis() {
        return now();
    }

    public static Timestamp timestamp() {
        return new Timestamp(now());
    }
}

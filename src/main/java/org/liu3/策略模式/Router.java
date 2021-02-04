package org.liu3.策略模式;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Author: liutianshuo
 * @Date: 2021/1/26
 */
public abstract class Router<T, R> {

    public interface StrategyMapper<T, R> {
        /**
         * 根据入参获取到对应的策略处理者。可通过 if-else 实现，也可通过 Map 实现。
         * @param param 入参
         * @return 策略处理者
         */
        Handler<T, R> get(T param);
    }

    private StrategyMapper<T, R> strategyMapper;

    @PostConstruct
    private void abstractInit() {
        strategyMapper = registerStrategyMapper();
        Objects.requireNonNull(strategyMapper, "strategyMapper cannot be null");
    }

    @SuppressWarnings("unchecked")
    private Handler<T, R> defaultStrategyHandler = Handler.DEFAULT;

    /**
     * 执行策略，框架会自动根据策略分发至下游的 Handler 进行处理
     * @param param 入参
     * @return 下游执行者给出的返回值
     */
    public R applyStrategy(T param) {
        final Handler<T, R> strategyHandler = strategyMapper.get(param);
        if (strategyHandler != null) {
            return strategyHandler.apply(param);
        }
        return defaultStrategyHandler.apply(param);
    }

    /**
     * 抽象方法，需要子类实现策略的分发逻辑     *     * @return 分发逻辑 Mapper 对象
     */
    protected abstract StrategyMapper<T, R> registerStrategyMapper();
}

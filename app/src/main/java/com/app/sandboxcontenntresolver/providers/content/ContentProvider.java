package com.app.sandboxcontenntresolver.providers.content;

/**
 * Базовый интерефейс для всех поставщиков данных
 * @param <T>
 */
public interface ContentProvider<T> {
    public T getResult(ContentProviderParams prams) throws Exception;
}

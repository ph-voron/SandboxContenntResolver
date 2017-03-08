package com.app.sandboxcontenntresolver.providers;

/**
 * Интерфейс для создания вобратного вызова с передачей результатов выполнения асинхронной задачи
 * @param <T> - тип возвращаемого значения
 */
public interface DataRequestListener<T> {
    /**
     * Вызывается в результате завершения выполнения таска
     * @param data - результат
     */
    public void onResult(T data);

    /**
     * Вызывается в случае, если в презультате выполнения возникло исключени
     * @param e - исключение
     */
    public void onException(Exception e);
}

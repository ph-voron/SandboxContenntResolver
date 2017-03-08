package com.app.sandboxcontenntresolver.providers;


import com.app.sandboxcontenntresolver.providers.content.ContentProvider;
import com.app.sandboxcontenntresolver.providers.content.ContentProviderParams;

public interface ContentProviderResolver {
    /**
     * Регистрация поставщика данных с привязкой к возвращаемой модели данных
     * @param provider - поставщик данных
     * @param modelType - тип возвращаемой модели данных
     */
    public void registerProvider(ContentProvider provider, Class<?> modelType);
    /**
     * Регистрация поставщика данных с привязкой к уникальному ключу
     * @param provider - поставщик данных
     * @param key - тип возвращаемой модели данных
     */
    public void registerProvider(ContentProvider provider, String key);
    /**
     * Выполнение запроса с передачей входных параметров к поставщику данных с получением результата обработки.
     * Поиск поставщика данных осуществляется по возвращаемой модели данных
     * @param typeof - тип значения, возвращаемый поставщиком
     * @param params - передаваемые параметры выполнения (не обязат.)
     * @return возвращает результат выполнения поставщиком данных запроса
     * @throws Exception исключение, которое может возникнуть в результате выполнения запроса к поставщику данных
     */
    public <T> T getResult(Class<T> typeof, ContentProviderParams params) throws Exception;
    /**
     * Выполнение запроса без передачи входных параметров к поставщику данных с получением результата обработки
     * Поиск поставщика данных осуществляется по возвращаемой модели данных
     * @param typeof - тип значения, возвращаемый поставщиком
     * @return возвращает результат выполнения поставщиком данных запроса
     * @throws Exception исключение, которое может возникнуть в результате выполнения запроса к поставщику данных
     */
    public <T> T getResult(Class<T> typeof) throws Exception;
    /**
     * Выполнение запроса с передачей входных параметров к поставщику данных с получением результата обработки.
     * Поиск поставщика данных осуществляется по уникальному строковому ключу, по которому был зарегистрирован
     * поставщик данных
     * @param params - передаваемые параметры выполнения (не обязат.)
     * @return возвращает результат выполнения поставщиком данных запроса
     * @throws Exception исключение, которое может возникнуть в результате выполнения запроса к поставщику данных
     */
    public <T> T getResult(String key, ContentProviderParams params) throws Exception;
    /**
     * Выполнение запроса без передачи входных параметров к поставщику данных с получением результата обработки
     * Поиск поставщика данных осуществляется по уникальному строковому ключу, по которому был зарегистрирован
     * @return возвращает результат выполнения поставщиком данных запроса
     * @throws Exception исключение, которое может возникнуть в результате выполнения запроса к поставщику данных
     */
    public <T> T getResult(String key) throws Exception;
}

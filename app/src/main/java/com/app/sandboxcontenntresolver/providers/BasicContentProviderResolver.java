package com.app.sandboxcontenntresolver.providers;


import android.content.Context;


import com.app.sandboxcontenntresolver.providers.content.ContentProvider;
import com.app.sandboxcontenntresolver.providers.content.ContentProviderParams;
import com.app.sandboxcontenntresolver.providers.content.GoogleGeocodeProvider;

import java.util.HashMap;


/**
 * Класс, разрешает зависимости клиент-провайдер. Предназначен для отчуждения модели от реализации
 * конкретного обработчика
 */
public class BasicContentProviderResolver implements ContentProviderResolver {
    //
    public static final String GEOCODE_REQUEST = "GEOCODE_REQUEST";
    //
    Context context = null;
    //реестр конечных провайдеров данных
    private HashMap<String, ContentProvider> mMap = null;
    //
    public BasicContentProviderResolver(Context context){
        this.context = context;
        mMap = new HashMap<String, ContentProvider>();
        //регистрация поставщикоа данных в конструкторе класса
        registerProvider(new GoogleGeocodeProvider(this.context), GEOCODE_REQUEST);
    }

    /**
     * Регистрация поставщика данных с привязкой к возвращаемой модели данных
     * @param provider - поставщик данных
     * @param modelType - тип возвращаемой модели данных
     */
    public void registerProvider(ContentProvider provider, Class<?> modelType){
        mMap.put(modelType.getName(), provider);
    }

    /**
     * Регистрация поставщика данных с привязкой к уникальному ключу
     * @param provider - поставщик данных
     * @param key - тип возвращаемой модели данных
     */
    public void registerProvider(ContentProvider provider, String key){
        mMap.put("key_"+key, provider);
    }

    /**
     * Выполнение запроса с передачей входных параметров к поставщику данных с получением результата обработки.
     * Поиск поставщика данных осуществляется по возвращаемой модели данных
     * @param typeof - тип значения, возвращаемый поставщиком
     * @param params - передаваемые параметры выполнения (не обязат.)
     * @return возвращает результат выполнения поставщиком данных запроса
     * @throws Exception исключение, которое может возникнуть в результате выполнения запроса к поставщику данных
     */
    public <T> T getResult(Class<T> typeof, ContentProviderParams params) throws Exception {
        ContentProvider provider = mMap.get(typeof.getName());
        return (T) provider.getResult(params);
    }
    /**
     * Выполнение запроса без передачи входных параметров к поставщику данных с получением результата обработки
     * Поиск поставщика данных осуществляется по возвращаемой модели данных
     * @param typeof - тип значения, возвращаемый поставщиком
     * @return возвращает результат выполнения поставщиком данных запроса
     * @throws Exception исключение, которое может возникнуть в результате выполнения запроса к поставщику данных
     */
    public <T> T getResult(Class<T> typeof) throws Exception {
        return getResult(typeof, null);
    }
    /**
     * Выполнение запроса с передачей входных параметров к поставщику данных с получением результата обработки.
     * Поиск поставщика данных осуществляется по уникальному строковому ключу, по которому был зарегистрирован
     * поставщик данных
     * @param params - передаваемые параметры выполнения (не обязат.)
     * @return возвращает результат выполнения поставщиком данных запроса
     * @throws Exception исключение, которое может возникнуть в результате выполнения запроса к поставщику данных
     */
    public <T> T getResult(String key, ContentProviderParams params) throws Exception {
        ContentProvider provider = mMap.get("key_" + key);
        return (T) provider.getResult(params);
    }
    /**
     * Выполнение запроса без передачи входных параметров к поставщику данных с получением результата обработки
     * Поиск поставщика данных осуществляется по уникальному строковому ключу, по которому был зарегистрирован
     * @return возвращает результат выполнения поставщиком данных запроса
     * @throws Exception исключение, которое может возникнуть в результате выполнения запроса к поставщику данных
     */
    public <T> T getResult(String key) throws Exception {
        return getResult(key, null);
    }
}

package com.app.sandboxcontenntresolver;

import android.content.Context;

import com.app.sandboxcontenntresolver.providers.BasicContentProviderResolver;
import com.app.sandboxcontenntresolver.providers.BasicRequestHandler;
import com.app.sandboxcontenntresolver.providers.ContentProviderResolver;
import com.app.sandboxcontenntresolver.providers.RequestHandler;

/**
 * Разрешает зависимости предоставляемых объектов и проводит первичную инициализацию
 */

public class DependencyResolver {
    private static Context appContext = null;
    private static RequestHandler requestHandler = null;
    private static BasicContentProviderResolver contentResolver = null;

    /**
     * Установка контекста
     */
    public static void setContext(Context context){
        appContext = context;
    }
    /**
     * Инициализирует и возвращает класс для разрешения зависимостей поставщиков данных
     * @return экземпляр ContentProviderResolver
     */
    public static ContentProviderResolver getContentResolver(){
        if(contentResolver == null){
            contentResolver = new BasicContentProviderResolver(appContext);
        }
        return contentResolver;
    }
    /**
     * Инициализирует и возвращает обработчик асинхронных запросов к поставщикам данных
     * @return экземпляр ContentProviderResolver
     */
    public static RequestHandler getRequestHandler(){
        if(requestHandler == null){
            requestHandler = new BasicRequestHandler(getContentResolver());
        }
        return requestHandler;
    }
}

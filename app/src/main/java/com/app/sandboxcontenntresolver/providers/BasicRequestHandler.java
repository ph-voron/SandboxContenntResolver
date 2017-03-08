package com.app.sandboxcontenntresolver.providers;

import android.location.Address;
import com.app.sandboxcontenntresolver.providers.content.parameters.GoogleGeocodeProviderParams;

import java.util.List;

/**
 * Асинхронный обработчик запросов данных приложения
 */
public class BasicRequestHandler extends BasicAsyncRequestHandler implements RequestHandler{
    private ContentProviderResolver contentProviderResolver = null;
    //
    public BasicRequestHandler(ContentProviderResolver contentProviderResolver){
        super();
        this.contentProviderResolver = contentProviderResolver;
    }

    /**
     * Получения данных из поставщика шеокодинга
     * @param params - входные параметы
     * @param listener - ссылка на подписчик обратного вызова с результатом операции
     */
    public void beginGoogleGeocode(final GoogleGeocodeProviderParams params,
                                                     final DataRequestListener<List<Address>> listener){
        startTask(new TaskValue<List<Address>>() {
            @Override
            public List<Address> run() throws Exception {
                return contentProviderResolver.getResult(BasicContentProviderResolver.GEOCODE_REQUEST, params);
            }
        }, listener);
    }
    //
    //

}

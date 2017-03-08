package com.app.sandboxcontenntresolver.providers;

import android.location.Address;


import com.app.sandboxcontenntresolver.providers.content.parameters.GoogleGeocodeProviderParams;

import java.util.List;

/**
 * Интерфейс обработчика запросов. Для примера оставлен один метод
 */
public interface RequestHandler {
    /**
     * Получение данных геокодера Google. Позволяет получить координаты из названия географического места или получить список мест
     * расположенных по указанным координатам
     * @param params - параметры для геокодера - наименование места илии координаты
     * @param listener - listener результатов выполнения со списком адресов
     */
    public void beginGoogleGeocode(final GoogleGeocodeProviderParams params,
                                   final DataRequestListener<List<Address>> listener);

    /**
     * Остановка всех тасков
     */
    public void cancelTasks();
}

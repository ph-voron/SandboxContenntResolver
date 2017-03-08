package com.app.sandboxcontenntresolver.providers;

import java.util.ArrayList;


/**
 * Базовый класс для построения обработчиков асинхронных запросов к поставщикам данных. Асинхронность
 * достигается путем создания отдельного потока выполения. Имеется возможность принудительной отмены всех выполняющихся тасков.
 */
public class BasicAsyncRequestHandler {
    /**
     * Интерфейс для создания "обертки" для метода который предполагается выполнить асинхронно
     */
    public interface TaskValue<T> {
        public T run() throws Exception;
    }

    /**
     * Исключение, выбрасываемое при принудительной отмене таска
     */
    public class CancellationException extends Exception {
    }

    /**
     * Токен отмены таска. Используется для отмены таска путем выбрасывания исключения
     */
    public class CancellationToken{
        private boolean canceled = false;
        public void cancel(){
            canceled = true;
        }
        //
        public boolean isCanceled(){
            return canceled;
        }
        //
        public void throwIfCancelled() throws CancellationException{
            if(canceled)
                throw new CancellationException();
        }
    }
    //Список токенов отмены по текущим таскам
    private ArrayList<CancellationToken> cancellationTokens = null;
    //
    public BasicAsyncRequestHandler(){
        cancellationTokens = new ArrayList<CancellationToken>();
    }

    /**
     * Запускает таск в отдельном потоке.
     * @param taskValue - ссылка на класс "обертки", в которую упокован код для асинхронного выполнения
     * @param listener - обратный вызов для возврата результатов выполения
     */
    public <T> void startTask(final TaskValue<T> taskValue, final DataRequestListener<T> listener){
        //инициализация токена отмены
        CancellationToken token = new CancellationToken();
        //создание нового потока
        Thread thread = new Thread(new Runnable() {
            CancellationToken token = null;
            @Override
            public void run() {
                T result = null;
                try {
                    //запуск упакованного таска
                    result = taskValue.run();
                    //проверка на принудительное завершение
                    token.throwIfCancelled();
                    if(listener != null){
                        //возврат результатов
                        listener.onResult(result);
                    }
                }
                catch (CancellationException e){

                }
                catch (Exception e){
                    if(listener != null){
                        listener.onException(e);
                        listener.onResult(null);
                    }
                }
                finally {
                    //удаление токена отменв после завершения таска
                    cancellationTokens.remove(token);
                }
            }
            //
            public Runnable setToken(CancellationToken token){
                this.token = token;
                return this;
            }
        }.setToken(token));
        //
        cancellationTokens.add(token);
        //
        thread.start();
    }

    /**
     * Остановка всех тасков
     */
    public void cancelTasks(){
        for(int i = 0; i < cancellationTokens.size(); i++){
            cancellationTokens.get(i).cancel();
        }
        cancellationTokens.clear();
    }
}

# SandboxContenntResolver
Приложение демонстрирует работу механизма асинхронного разрешения запросов к пулу поставщиков данных из UI
Данный пример испольуется для демонстрации части системы (BasicRequestHandler, BasicContentProviderResolver и т.д)
обработки запросов к контент-провайдерам приложения. Частично реализует подход IoC.
Такая реализация была выбрана в виду того, что в целевом приложении (из которого взят код) на момент начала разработки
заказчиком часто менялись источники данных, их количество и типы данных и требования
(это было оговорено заранее). Для того, что бы можно было сделать систему максимально гибкой и
не критичной к постоянным изменениям требований была выбрана такая реализация.
Для демонстрации в примере использован только один content provider (GoogleGeocodeProvider)

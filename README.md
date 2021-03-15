# CardDeliveryChangeDate [![Build status](https://ci.appveyor.com/api/projects/status/5xdj6qmhhg20jmu5?svg=true)](https://ci.appveyor.com/project/ks1109b/carddeliverychangedate)

### Задача

Автоматизировать тестирование новой функции формы заказа доставки карты.

### Результат

1. Автоматизировано тестирование нового функционала формы заказа доставки карты.
2. Проведено регрессионное тестирование.
3. [Предыдущие тесты](https://github.com/ks1109b/CardDelivery/blob/main/src/test/java/ru/netology/web/CallbackTest.java) адаптированы под новый код.
4. Выявлен ряд [багов](https://github.com/ks1109b/CardDeliveryChangeDate/blob/main/README.md#баги).

### Баги

1. [Орфографическая ошибка в форме заявки на доставку карты](https://github.com/ks1109b/CardDeliveryChangeDate/issues/1)
2. [Форма заказа карты с доставкой принимает невалидное значение в поле "Мобильный телефон"](https://github.com/ks1109b/CardDeliveryChangeDate/issues/2)
3. [Форма заявки на карту с доставкой не принимает букву "Ё" в имени клиента](https://github.com/ks1109b/CardDeliveryChangeDate/issues/3)

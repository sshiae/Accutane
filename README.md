# Техническое задание для разработки приложения Accutane

## Общая информация

**Название приложения:** Accutane

**Цель:** Приложение предназначено для удобного отслеживания курса приема ретиноидов. Оно позволяет пользователям отмечать дни приема, получать напоминания о необходимости приема, видеть дату окончания курса, а также отслеживать общую кумулятивную дозу и текущую набранную дозу.

## Основной функционал

### 1. Стартовое окно

- **Описание:** При первом запуске приложение отображает окно со списком курсов.
- **Содержимое:**
    - Список курсов с названием и датой начала.
    - Возможность удаления, просмотра и изменения курсов.
    - Плавающая кнопка в правом нижнем углу для добавления нового курса.

### 2. Добавление курса

- **Доступ:** Через плавающую кнопку на стартовом экране.
- **Поля для ввода:**
    - Дневная дозировка.
    - Общая желаемая дозировка.
    - Дата начала курса.
    - Время напоминания о приеме препарата.
    - Наименование курса.
- **Действие:** Кнопка "Сохранить" для добавления курса.

### 3. Просмотр и редактирование курса

- **Доступ:** При нажатии на курс в списке.
- **Информация:**
    - Процентный прогресс курса в виде круговой диаграммы.
    - Общее кол-во дней лечения.
    - Дата начала курса.
    - Суточная доза препарата.
    - Уже накопленная курсовая доза.
    - Количество дней до конца курса (рассчитывается как общая доза / суточную дозу, округляем вверх).
    - Общая желаемая доза.
- **Действия:**
    - Кнопка "Изменить курс": открывает окно редактирования курса (см. пункт 2).
    - Кнопка "Прекратить курс": удаляет курс из списка.

### 4. Напоминания

- **Описание:** Ежедневные напоминания о приеме капсулы.
- **Функции:**
    - Уведомление с возможностью выбора "Напомнить через 5 мин" и "Закрыть".
    - При выборе "Напомнить через 5 мин" уведомление повторяется через 5 минут.
    - При выборе "Закрыть" доза засчитывается как выпитая, и открывается приложение.

## Технические требования

1. **Платформы:** Android и iOS.
2. **Язык разработки:** Kotlin и KMP.
3. **Уведомления:** Использовать системные уведомления для напоминаний.
4. **Интерфейс:** Должен быть интуитивно понятным и соответствовать современным стандартам мобильных приложений.
5. **Безопасность:** Обеспечить защиту данных пользователей.

## Дополнительные требования

- Реализовать возможность локализации приложения для поддержки нескольких языков.
- Обеспечить адаптивный дизайн для различных размеров экранов.

## Контроль качества

- Провести тестирование на всех поддерживаемых устройствах.
- Убедиться в корректной работе всех функций и отсутствии критических ошибок.
- Провести тестирование пользовательского интерфейса на удобство использования.

## Сроки выполнения

- Разработка: 3 месяца.
- Тестирование: 1 месяц.
- Внедрение и публикация: 1 месяц.
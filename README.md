# Проект `ShareIt`.
## Спринт №13
<details> <summary> Техническое задание. Часть 1 </summary>  

В этом модуле вы будете создавать сервис для шеринга (от англ. _**share**_ — «делиться») вещей.
Шеринг как экономика совместного использования набирает сейчас всё большую полярность.
Если в 2014 году глобальный рынок шеринга оценивался всего в $15 млрд, то к 2025 может достигнуть $335 млрд.

Почему шеринг так популярен. Представьте, что на воскресной ярмарке вы купили несколько картин и
хотите повесить их дома. Но вот незадача — для этого нужна дрель, а её у вас нет. Можно, конечно,
пойти в магазин и купить, но в такой покупке мало смысла — после того, как вы повесите картины,
дрель будет просто пылиться в шкафу. Можно пригласить мастера — но за его услуги придётся заплатить.
И тут вы вспоминаете, что видели дрель у друга. Сама собой напрашивается идея — одолжить её.

Большая удача, что у вас оказался друг с дрелью и вы сразу вспомнили про него!
А не то в поисках инструмента пришлось бы писать всем друзьям и знакомым.
Или вернуться к первым двум вариантам — покупке дрели или найму мастера.
Насколько было бы удобнее, если бы под рукой был сервис, где пользователи делятся вещами!
Созданием такого проекта вы и займётесь.
## Что должен уметь новый сервис
Ваш проект будет называться ShareIt. Он должен обеспечить пользователям, во-первых,
возможность рассказывать, какими вещами они готовы поделиться, а во-вторых, находить нужную вещь
и брать её в аренду на какое-то время.

Сервис должен не только позволять бронировать вещь на определённые даты, но и
закрывать к ней доступ на время бронирования от других желающих.
На случай, если нужной вещи на сервисе нет, у пользователей должна быть возможность оставлять запросы.
Вдруг древний граммофон, который странно даже предлагать к аренде, неожиданно понадобится
для театральной постановки. По запросу можно будет добавлять новые вещи для шеринга.

## Каркас приложения
В этом спринте от вас требуется создать каркас приложения, а также разработать часть его веб-слоя.
Основная сущность сервиса, вокруг которой будет строиться вся дальнейшая работа, — вещь.
В коде она будет фигурировать как `Item`.

Пользователь, который добавляет в приложение новую вещь, будет считаться ее владельцем.
При добавлении вещи должна быть возможность указать её краткое название и добавить небольшое описание.
К примеру, название может быть — `«Дрель “Салют”»`, а описание — `«Мощность 600 вт, работает ударный режим,
так что бетон возьмёт»`. Также у вещи обязательно должен быть статус — доступна ли она для аренды.
Статус должен проставлять владелец.

Для поиска вещей должен быть организован поиск. Чтобы воспользоваться нужной вещью,
её требуется забронировать. Бронирование, или `Booking` — ещё одна важная сущность приложения.
Бронируется вещь всегда на определённые даты. Владелец вещи обязательно должен подтвердить
бронирование.

После того как вещь возвращена, у пользователя, который её арендовал, должна быть возможность оставить отзыв.
В отзыве можно поблагодарить владельца вещи и подтвердить, что задача выполнена — дрель успешно
справилась с бетоном, и картины повешены.

Ещё одна сущность, которая вам понадобится, — запрос вещи `ItemRequest`.
Пользователь создаёт запрос, если нужная ему вещь не найдена при поиске.
В запросе указывается, что именно он ищет. В ответ на запрос другие пользовали могут добавить нужную вещь.

У вас уже готов шаблон проекта с использованием Spring Boot. Создайте ветку `add-controllers` и
переключитесь на неё — в этой ветке будет вестись вся разработка для первого спринта.

## Реализация модели данных
В этом модуле вы будете использовать структуру не по типам классов, а по
фичам (англ. _**Feature layout**_) — весь код для работы с определённой сущностью должен быть
в одном пакете. Поэтому сразу создайте четыре пакета — `item`, `booking`, `request` и `user`.
В каждом из этих пакетов будут свои контроллеры, сервисы, репозитории и другие классы,
которые вам понадобятся в ходе разработки. В пакете `item` создайте класс `Item`.

## Создание DTO-объектов и мапперов
Созданные объекты `Item` и `User` вы в дальнейшем будете использовать для работы с базой
данных (это ждёт вас в следующем спринте). Сейчас, помимо них, вам также понадобятся объекты,
которые вы будете возвращать пользователям через REST-интерфейс в ответ на их запросы.

Разделять объекты, которые хранятся в базе данных и которые возвращаются пользователям, — хорошая
практика. Например, вы можете не захотеть показывать пользователям владельца вещи (поле `owner`),
а вместо этого возвращать только информацию о том, сколько раз вещь была в аренде.
Чтобы это реализовать, нужно создать отдельную версию каждого класса, с которой будут работать
пользователи, — DTO (_**D**ata **T**ransfer **O**bject_).

Кроме DTO-классов, понадобятся Mapper-классы — они помогут преобразовывать объекты модели
в DTO-объекты и обратно. Для базовых сущностей `Item` и `User` создайте Mapper-класс и метод
преобразования объекта модели в DTO-объект.

## Разработка контроллеров
Когда классы для хранения данных будут готовы, DTO и мапперы написаны, можно перейти
к реализации логики. В приложении будет три классических слоя — контроллеры, сервисы
и репозитории. В этом спринте вы будете работать преимущественно с контроллерами.

Для начала научите ваше приложение работать с пользователями. Ранее вы уже создавали
контроллеры для управления пользователями — создания, редактирования и просмотра.
Здесь вам нужно сделать то же самое. Создайте класс `UserController` и методы в нём
для основных CRUD-операций. Также реализуйте сохранение данных о пользователях в памяти.

Далее переходите к основной функциональности этого спринта — работе с вещами.
Вам нужно реализовать добавление новых вещей, их редактирование, просмотр списка вещей и поиск.
Создайте класс `ItemController`. В нём будет сосредоточен весь REST-интерфейс для работы с вещью.

Вот основные сценарии, которые должно поддерживать приложение.
* Добавление новой вещи. Будет происходить по эндпойнту `POST /items`. На вход поступает объект `ItemDto`.
  `userId` в заголовке `X-Sharer-User-Id` — это идентификатор пользователя, который добавляет вещь.
  Именно этот пользователь — владелец вещи. Идентификатор владельца будет поступать на вход
  в каждом из запросов, рассмотренных далее.
* Редактирование вещи. Эндпойнт `PATCH /items/{itemId}`. Изменить можно название, описание и
  статус доступа к аренде. Редактировать вещь может только её владелец.
* Просмотр информации о конкретной вещи по её идентификатору. Эндпойнт `GET /items/{itemId}`.
  Информацию о вещи может просмотреть любой пользователь.
* Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой.
  Эндпойнт `GET /items`.
* Поиск вещи потенциальным арендатором. Пользователь передаёт в строке запроса текст, и система
  ищет вещи, содержащие этот текст в названии или описании. Происходит по
  эндпойнту /items/search?text={text}, в `text` передаётся текст для поиска.
  Проверьте, что поиск возвращает только доступные для аренды вещи.

Для каждого из данных сценариев создайте соответственный метод в контроллере. Также создайте
интерфейс `ItemService` и реализующий его класс `ItemServiceImpl`, к которому будет обращаться
ваш контроллер. В качестве DAO создайте реализации, которые будут хранить данные в памяти приложения.
Работу с базой данных вы реализуете в следующем спринте.

## Тестирование
Для проверки кода мы подготовили
[Postman-коллекцию](https://github.com/yandex-praktikum/java-shareit/blob/add-controllers/postman/sprint.json).
С её помощью можно протестировать ваше API и убедиться, что все запросы успешно выполняются.
## Дополнительные советы ментора
Если задание показалось вам недостаточно подробным, вы можете обратиться к этому файлу:
[Дополнительные советы ментора](https://code.s3.yandex.net/Java/4mod1sprProject/mentors_advice_1.2.pdf).

В нём вы найдёте дополнительную информацию о том, как выполнить задание спринта. Но помните:
реальные ТЗ часто скупы на подробности, поэтому разработчику приходится самостоятельно принимать
некоторые архитектурные решения. Чем раньше вы научитесь определять минимальные требования,
необходимые для начала разработки проекта, тем проще вам будет работать в команде над реальным
проектом.

На этом пока всё! Но и это немало для хорошего старта! Удачного программирования!
</details>  

### Commits:  
#### Commit №13.1:  
feat:  
-Создан пакет booking, содержащий файлы классов BookingDto, BookingMapper, Booking и BookingController;  
-Создан пакет item, содержащий файлы классов ItemDto, ItemMapper, Item, ItemRepositoryImpl, ItemService, ItemController и файл интерфейса ItemRepository;  
-Создан пакет request, содержащий файлы классов ItemRequestDto, ItemRequestMapper, ItemRequest и ItemRequestController;  
-Создан пакет user, содержащий файлы классов UserDto, UserMapper, User, UserRepositoryImpl и файл интерфейса UserRepository;  
-Создан пакет exception, содержащий файлы классов ItemNotFoundException, UserAlreadyExistsException, UserNotFoundException и ValidationException;  
-Создан пакет handler, содержащий файл класса ErrorHandler.  

#### Commit №13.2:  
refactor:  
-исключения UserNotFoundException и ItemNotFoundException объеденины в одно;  
-переработан класс BookingDto;  
-переработан класс BookingMapper;  
-переработан класс ErrorHandler;  
-переработан класс ItemDto;  
-переработан класс ItemMapper;  
-переработан класс Item;  
-переработан интерфейс ItemRepository;  
-переработан класс ItemRepositoryImpl;  
-переработан класс ItemService;  
-переработан класс ItemController;  
-переработан класс ItemRequestDto;  
-переработан класс ItemRequestMapper;  
-переработан класс ItemRequest;  
-переработан класс UserDto;  
-переработан класс UserMapper;  
-переработан класс User;  
-переработан интерфейс UserRepository;  
-переработан класс UserRepositoryImpl;  
-переработан класс UserService.  

#### Commit №13.3:
refactor:
-переработан класс AlreadyExistsException;  
-переработан класс ItemRepositoryImpl;  
-переработан класс ItemService;  
-переработан класс UserRepositoryImpl;  
-переработан класс UserService.  

## Спринт №14
<details> <summary> Техническое задание. Часть 2 </summary>  

В прошлом спринте вы приступили к проекту `ShareIt` и уже сделали немало — например,
реализовали слой контроллеров для работы с вещами. В этот раз вы продолжите
совершенствовать сервис, так что он станет по-настоящему полезным для пользователей.  

Перед вами две большие задачи: добавить работу с базой данных в уже реализованную часть
проекта, а также дать пользователям возможность бронировать вещи.

### Немного подготовки
В этом спринте разработка будет вестись в ветке `add-bookings`. Создайте ветку с таким
названием и переключитесь на неё.  

Далее переходите к настройке базы данных. Пришло время использовать **Hibernate** и **JPA**
самостоятельно. Для начала добавьте зависимость `spring-boot-starter-data-jpa` и драйвер
`postgresql` в файл `pom.xml`.

### Создание базы данных
Теперь поработайте над структурой базы данных. В ней будет по одной таблице для каждой
из основных сущностей, а также таблица, где будут храниться отзывы.  

Подумайте, какой тип данных **PostgreSQL** лучше подойдёт для каждого поля. В качестве подсказки
проанализируйте таблицы, которые были использованы в приложении `Later`.

Напишите SQL-код для создания всех таблиц и сохраните его в файле `resources/schema.sql`
— **Spring Boot** выполнит содержащийся в нём скрипт на старте проекта. На данный момент вам
достаточно создать таблицы для двух сущностей, которые вы уже разработали — `Item` и `User`.  

Важный момент: приложение будет запускаться много раз, и каждый раз **Spring** будет выполнять
`schema.sql`. Чтобы ничего не сломать и не вызвать ошибок, все конструкции в этом файле
должны поддерживать множественное выполнение. Это значит, что для создания таблиц следует
использовать не просто конструкцию `CREATE TABLE`, но `CREATE TABLE IF NOT EXIST` — тогда
таблица будет создана, только если её ещё не существует в базе данных.  

**Подсказка: пример кода для создания таблицы `users`**
```sql
CREATE TABLE IF NOT EXISTS users (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
name        VARCHAR(255) NOT NULL,
email       VARCHAR(512) NOT NULL,
CONSTRAINT  pk_user PRIMARY KEY (id),
CONSTRAINT  UQ_USER_EMAIL UNIQUE (email)
);
```

### Настройка JPA
Пора подготовить сущности к работе с базой данных. Мы говорили, что для этого используют
аннотации JPA: `@Entity`, `@Table,` `@Column,` `@Id`. Для поля status в классе `Booking`
вам также пригодится `@Enumerated`. Добавьте соответствующие аннотации для сущностей.

Создайте репозитории для `User` и `Item` и доработайте сервисы, чтобы они работали с
новыми репозиториями.

**Подсказка: маппинг между столбцами БД и моделью данных**
Если название поля в модели отличается от имени поля в базе, нужно обязательно указать
маппинг между ними с помощью аннотации `@Column`.

### Реализация функции бронирования
Чтобы сделать приложение ещё более полезным и интересным, добавьте возможность брать вещи
в аренду на определённые даты.  

Вот основные сценарии и эндпоинты:  
- Добавление нового запроса на бронирование. Запрос может быть создан любым пользователем,
а затем подтверждён владельцем вещи. Эндпоинт — `POST /bookings`. После создания запрос
находится в статусе `WAITING` — «ожидает подтверждения».  
- Подтверждение или отклонение запроса на бронирование. Может быть выполнено только
владельцем вещи. Затем статус бронирования становится либо `APPROVED`, либо `REJECTED`.
Эндпоинт — `PATCH /bookings/{bookingId}?approved={approved}`, параметр `approved` может
принимать значения `true` или `false`.  
- Получение данных о конкретном бронировании (включая его статус). Может быть выполнено
либо автором бронирования, либо владельцем вещи, к которой относится бронирование.
Эндпоинт — `GET /bookings/{bookingId}`.  
- Получение списка всех бронирований текущего пользователя. Эндпоинт —
`GET /bookings?state={state}`. Параметр `state` необязательный и по умолчанию равен **ALL**
(англ. «все»). Также он может принимать значения **CURRENT** (англ. «текущие»),
**PAST** (англ. «завершённые»), **FUTURE** (англ. _«будущие»_), **WAITING** (англ. _«ожидающие
подтверждения»_), **REJECTED** (англ. _«отклонённые»_). Бронирования должны возвращаться
отсортированными по дате от более новых к более старым.  
- Получение списка бронирований для всех вещей текущего пользователя. Эндпоинт —
`GET /bookings/owner?state={state}`. Этот запрос имеет смысл для владельца хотя бы одной вещи.
Работа параметра `state` аналогична его работе в предыдущем сценарии.  

Для начала добавьте в модель данных сущность `Booking` и код для создания соответствующей таблицы
в файл `resources/schema.sql`.  

Создайте контроллер `BookingController` и методы для каждого из описанных сценариев. Подумайте,
не нужно ли написать дополнительные DTO-классы для каких-то сценариев.  

Кроме контроллеров, необходимо реализовать хранение данных — то есть сервисы и репозитории.

**Подсказка: какие могут быть изменения в DTO**  
Например, может быть полезно создать отдельное перечисление для возможных методов параметра
`state`, ведь задачи этого перечисления могут отличаться в слое представления (параметр для
поиска) и в модели данных (состояние бронирования).

### Добавление дат бронирования при просмотре вещей
Осталась пара штрихов. Итак, вы добавили возможность бронировать вещи. Теперь нужно, чтобы
владелец видел даты последнего и ближайшего следующего бронирования для каждой вещи, когда
просматривает список (`GET /items`).

### Добавление отзывов
Мы обещали, что пользователи смогут оставлять отзывы на вещь после того, как взяли её в аренду.
Пришло время добавить и эту функцию!  

В базе данных уже есть таблица `comments`. Теперь создайте соответствующий класс модели данных
`Comment` и добавьте необходимые аннотации `JPA`. Поскольку отзыв — вспомогательная сущность
и по сути часть вещи, отдельный пакет для отзывов не нужен. Поместите класс в пакет item.

Комментарий можно добавить по эндпоинту `POST /items/{itemId}/comment`, создайте в контроллере
метод для него.

Реализуйте логику по добавлению нового комментария к вещи в сервисе `ItemServiceImpl`. Для этого
также понадобится создать интерфейс `CommentRepository`. Не забудьте добавить проверку, что
пользователь, который пишет комментарий, действительно брал вещь в аренду.  

Осталось разрешить пользователям просматривать комментарии других пользователей. Отзывы можно
будет увидеть по двум эндпоинтам — по `GET /items/{itemId}` для одной конкретной вещи и
по `GET /items` для всех вещей данного пользователя.

### Тестирование
Для проверки всей функциональности, которую вы добавили в этом спринте, мы подготовили
[Postman-коллекцию](https://github.com/yandex-praktikum/java-shareit/blob/add-bookings/postman/sprint.json)
— используйте её для тестирования приложения.

### Дополнительные советы ментора
Как и в прошлом задании спринта, более подробную информацию вы найдёте в файле:
[Дополнительные советы ментора.](https://code.s3.yandex.net/Java/14sprint/MentorsAdvice_05_04_23v4.pdf)

На этом пока всё: вы отлично потрудились! Как всегда, интересного вам программирования!
</details>

![er-diagram](er_diagram.png)

### Commits:
#### Commit №14.1:
feat:  
-Добавлена БД;  
-Добавлена реализация аренды (Booking).

refactor:  
-Полный рефактор приложения.  

#### Commit №14.2:
refactor:
-переработан файл pom.xml;  
-переработан файл application.properties;  
-переработан класс BookingController;  
-переработан класс BookingMapper;  
-переработан интерфейс BookingRepository;  
-переработан класс BookingService;  
-переработан класс CommentMapper;  
-переработан класс ItemController;  
-переработан класс ItemMapper;  
-переработан класс Item;  
-переработан класс ItemService;  
-переработан класс UserController;  
-переработан класс UserMapper;  
-переработан класс UserService.  

#### Commit №14.3:
refactor:  
-переработан класс BookingMapper;  
-переработан интерфейс BookingRepository;  
-переработан класс BookingService;  
-переработан класс CommentMapper;  
-переработан класс ItemMapper;  
-переработан класс ItemService;  
-переработан класс UserMapper;  
-переработан класс UserService.  
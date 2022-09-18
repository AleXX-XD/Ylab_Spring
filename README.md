1. собрать приложение mvn clean install
2. проверить работу приложения возможно по этому ендпоинту: http://localhost:8091/app/actuator
3. посмотреть сваггер возможно тут: http://localhost:8091/app/swagger-ui/index.html

* ### Добавление пользователя с книгами:
http://localhost:8091/app/api/v1/user/create

rqid requestId01

{ "userRequest": {
"fullName": "Viktor",
"title": "reader",
"age": 33
},
"bookRequests": [
{
"title": "War and Peace",
"author": "Tolstoy",
"pageCount": 222
},
{
"title": "Viy",
"author": "Gogol",
"pageCount": 251
},
{},
{
"title": "The Brothers Karamazov",
"author": "Dostoevsky",
"pageCount": 485
}
]
}

rqid requestId02

{ "userRequest": {
"fullName": "Maksim",
"title": "reader",
"age": 25
},
"bookRequests": [
{
"title": "Idiot",
"author": "Dostoevsky",
"pageCount": 562
},
{
"title": "Dead Souls",
"author": "Gogol",
"pageCount": 327
},
{
"title": "Captain's Daughter",
"author": "Pushkin",
"pageCount": 555
}
]
}

rqid requestId03

{ "userRequest": {
"fullName": "Andrey",
"title": "reader",
"age": 52
},
"bookRequests": []
}

* ### Обновление данных пользователя и его книг:
(В url добавлено: /{userId} , т.к. все остальные параметры могут нуждаться в обновлении)
http://localhost:8091/app/api/v1/user/update/1

{ "userRequest": {
"fullName": "NewViktor",
"title": "reader",
"age": 38
},
"bookRequests": [
{
"title": "New War and Peace",
"author": "Tolstoy",
"pageCount": 202
},
{
"title": "The Brothers Karamazov",
"author": "New Dostoevsky",
"pageCount": 485
}
]
}

* ### Вывод пользователя по id с его книгами:
http://localhost:8091/app/api/v1/user/get/1


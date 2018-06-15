Инструкция для запуска Test System
1. создать базу данных "testing" в MariaDb Server
2. в файле resousers/application.properties ввести логин и пароль от базы данных
ПРИМЕР:
spring.datasource.username=root
spring.datasource.password=root

3. запустить проект

/----
P.S Дампа БД нет, т.к. все поля создаются 
в процессе работы программы (регистрация, создание тестов)
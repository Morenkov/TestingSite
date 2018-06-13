<#import "loginReg.ftl" as l>


<ul class="nav nav-pills">
    <li class="nav-item">
        <a class="nav-link active" href="/">Test System</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/main">Тесты</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/user-tests/0">Создать тест</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/login">Вход</a>
    </li>
    <li class="nav-item">
        <a class="nav-tabs">
            Сейчас вы: <#if currentUser??>${currentUser.username} <#else>Гость(Неопознанная Панда)</#if>
        </a>
    </li>
    <li class="nav-item">
        <div class="nav-link disabled">
        <@l.logout />
        </div>
    </li>
</ul>


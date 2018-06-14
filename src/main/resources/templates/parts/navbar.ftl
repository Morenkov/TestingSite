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
<#if currentUser??>
    <li class="nav-item">
<<<<<<< HEAD
        <a class="nav-link" href="/profile">
            Текущий пользователь: <#if currentUser??>${currentUser.username} <#else>Гость</#if>
        </a>
    </li>
    <li class="nav-item active">
        <a class="nav-link" href="/profile">Профиль</a>
    </li>
=======
        <a class="nav-link" href="/login">Вход</a>
    </li>
    <li class="nav-item">
        <a class="nav-tabs">
            Сейчас вы: <#if currentUser??>${currentUser.username} <#else>Гость(Неопознанная Панда)</#if>
        </a>
    </li>
>>>>>>> parent of 15adf41... 4
    <li class="nav-item">
        <div class="nav-link disabled">
        <@l.logout />
        </div>
    </li>
</ul>


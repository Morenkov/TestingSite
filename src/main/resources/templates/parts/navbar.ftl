<#import "loginReg.ftl" as l>


<ul class="nav nav-pills mt-3">
    <li class="nav-item">
        <a class="nav-link active" href="/">TestSyst</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/main">Тесты</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/setSizes">Создать тест</a>
    </li>
<#if currentUser??>
    <li class="nav-item">
        <a class="nav-link" href="/profile">
            Текущий пользователь: <#if currentUser??>${currentUser.username} <#else>Гость</#if>
        </a>
    </li>
    <li class="nav-item active">
        <a class="nav-link" href="/profile">Профиль</a>
    </li>
    <li class="nav-item">
        <div class="nav-link disabled">
        <@l.logout />
        </div>
    </li>
</#if>
</ul>


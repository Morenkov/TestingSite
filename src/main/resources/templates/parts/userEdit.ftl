<#macro userEdit>

User editor
<div class="form-group">
    <form action="/profile" method="post">
        <div class="form-row">
            <p class="h4">${user.username}</p>
        </div>
        <div class="form-row form-group">
            <input type="text" name="password" value="" placeholder="Введите новый пароль">
        </div>
        <div class="form-row form-group">
            <input type="text" name="firstName" value="${user.firstName}">
        </div>
        <div class="form-row form-group">
            <input type="text" name="lastName" value="${user.lastName}">
        </div>
        <div class="form-row form-group">
            <input type="text" name="thirdName" value="${user.thirdName}">
        </div>
        <div class="form-row form-group">
            <input type="text" name="country" value="${user.country}">
        </div>
        <div class="form-row">
            <input type="text" name="city" value="${user.city}">
        </div>
        <div class="form-row form-group">
            <button class="btn btn-primary mt-3" type="submit">Save</button>
        </div>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
    </form>
</div>
</#macro>

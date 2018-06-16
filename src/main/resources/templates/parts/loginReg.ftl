<#macro login path isRegisterForm>

<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm-6">
            <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                   class="form-control ${(usernameError??)?string('is-invalid', '')}"
                   placeholder="User name"/>
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Пароль:</label>
        <div class="col-sm-6">
            <input type="password" name="password"
                   class="form-control ${(passwordError??)?string('is-invalid', '')}"
                   placeholder="Password"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
    </div>
    <#if isRegisterForm>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Повтороите пароль :</label>
            <div class="col-sm-6">
                <input type="password" name="password2"
                       class="form-control ${(password2Error??)?string('is-invalid', '')}"
                       placeholder="Retype password"/>
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Имя :</label>
            <div class="col-sm-6">
                <input type="text" name="firstName" value="<#if user??>${user.firstName}</#if>"
                       class="form-control ${(firstNameError??)?string('is-invalid', '')}"
                       placeholder="имя"/>
            <#if firstNameError??>
                <div class="invalid-feedback">
                    ${firstNameError}
                </div>
            </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Фамилия :</label>
            <div class="col-sm-6">
                <input type="text" name="lastName" value="<#if user??>${user.lastName}</#if>"
                       class="form-control ${(lastNameError??)?string('is-invalid', '')}"
                       placeholder="фамилия"/>
            <#if lastNameError??>
                <div class="invalid-feedback">
                    ${lastNameError}
                </div>
            </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Отчество :</label>
            <div class="col-sm-6">
                <input type="text" name="thirdName" value="<#if user??>${user.thirdName}</#if>"
                       class="form-control ${(thirdNameError??)?string('is-invalid', '')}"
                       placeholder="отчество"/>
            <#if thirdNameError??>
                <div class="invalid-feedback">
                    ${thirdNameError}
                </div>
            </#if>
            </div>
        </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Страна :</label>
                <div class="col-sm-6">
                    <input type="text" name="country" value="<#if user??>${user.country}</#if>"
                           class="form-control ${(countryError??)?string('is-invalid', '')}"
                           placeholder="название страны"/>
            <#if countryError??>
                <div class="invalid-feedback">
                    ${countryError}
                </div>
            </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Город :</label>
                <div class="col-sm-6">
                    <input type="text" name="city" value="<#if user??>${user.city}</#if>"
                           class="form-control ${(cityError??)?string('is-invalid', '')}"
                           placeholder="Город"/>
            <#if cityError??>
                <div class="invalid-feedback">
                    ${cityError}
                </div>
            </#if>
                </div>
            </div>
    </#if>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <#if !isRegisterForm><a href="/registration">Регистрация</a></#if>
    <button class="btn btn-primary" type="submit"><#if isRegisterForm>Регистрация<#else>Войти</#if></button>
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit">Выйти</button>
</form>
</#macro>

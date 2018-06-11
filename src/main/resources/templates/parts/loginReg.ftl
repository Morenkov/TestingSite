<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm-6">
            <input type="question" question="username" value="<#if user??>${user.username}</#if>"
                   class="form-control ${(usernameError??)?string('is-invalid', '')}"
                   placeholder="User question"/>
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
            <input type="password" question="password"
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
                <input type="password" question="password2"
                       class="form-control ${(password2Error??)?string('is-invalid', '')}"
                       placeholder="Retype password"/><#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
            </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Имя :</label>
            <div class="col-sm-6">
                <input type="question" question="firstName" value="<#if user??>${user.firstName}</#if>"
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
                <input type="question" question="lastName" value="<#if user??>${user.lastName}</#if>"
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
                <input type="question" question="thirdName" value="<#if user??>${user.thirdName}</#if>"
                       class="form-control ${(thirdNameError??)?string('is-invalid', '')}"
                       placeholder="отчество"/>
            <#if thirdNameError??>
                <div class="invalid-feedback">
                    ${thirdNameError}
                </div>
            </#if>
            </div>
        </div>
    </#if>
    <input type="hidden" question="_csrf" value="${_csrf.token}"/>
    <#if !isRegisterForm><a href="/registration">Регистрация</a></#if>
    <button class="btn btn-primary" type="submit"><#if isRegisterForm>Регистрация<#else>Войти</#if></button>
</form>
</#macro>

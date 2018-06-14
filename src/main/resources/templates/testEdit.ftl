<#import "parts/common.ftl" as c>
<@c.page>

<h3>Test editor</h3>
</a>
    <div class="form-group">

        <form action="/user-tests/<#if test??>${test.id}<#else>0</#if>" method="post" enctype="multipart/form-data">

            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-info" id="basic-addon1">Название теста: </span>
                </div>
                <input type="text" class="form-control"
                        value="<#if test??>${test.name}" name="t-${test.id}</#if>" placeholder="Введите имя теста">
            </div>

            <ol>
    <#list test.questions as question>
        <li>

            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-success text-light" id="basic-addon1">Вопрос: </span>
                </div>
                <input type="text" class="form-control"
                       value="${question.name}" name="q-${question.id}" placeholder="Введите вопрос">
            </div>

            <div class="form-group">
        <#list question.answers as answer>

            <input type="text" class="form-control border border-primary"
                   value="${answer.name}" name="a-${answer.id}" placeholder="Введите ответ"/>
            <select name="p-${answer.id}" class="selectpicker mt-1">
                <option value="0" selected>0</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>

        <#else>
            No answers
        </#list>
            </div>

        </li>
    <#else>
        No questions
    </#list>
            </ol>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if test??>${test.id}</#if>"/>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save test</button>
            </div>
        </form>
    </div>
</@c.page>


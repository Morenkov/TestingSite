<#macro testRender path isEditForm>
 <div class="form-group">

     <div class="label"><#if errors??>Ошибка: возможно слишком длинное имя или пустота</#if></div>

     <form action="${path}" method="post" enctype="multipart/form-data">

         <div class="input-group mb-3">

                <#if isEditForm>
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-info" id="basic-addon1">Название теста: </span>
                    </div>
                    <input type="text" class="form-control"
                            value="<#if test??>${test.name}" name="t-${test.id}</#if>" placeholder="Введите имя теста">
                <#else>
                <h3>Тест № ${test.id} - ${test.name}"</h3>
                </#if>
         </div>

         <ol>
    <#list test.questions as question>
        <li>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-success text-light" id="basic-addon1">Вопрос: </span>
                </div>
                    <#if isEditForm>
                        <input type="text" class="form-control" value="${question.name}" name="q-${question.id}"
                               placeholder="Введите вопрос">
                    <#else>
                        <p type="text" class="form-control">${question.name}</p>
                    </#if>
            </div>

            <div class="form-group">
        <#list question.answers as answer>
            <#if isEditForm>
            <input type="text" class="form-control border border-primary"
                   value="${answer.name}" name="a-${answer.id}"
                   placeholder="Введите ответ"/>
            <select name="p-${answer.id}" class="selectpicker mt-1">
                <option value="0" <#if answer.points == 0>selected</#if>>0</option>
                <option value="1" <#if answer.points == 1>selected</#if>>1</option>
                <option value="2" <#if answer.points == 2>selected</#if>>2</option>
                <option value="3" <#if answer.points == 3>selected</#if>>3</option>
                <option value="4" <#if answer.points == 4>selected</#if>>4</option>
            </select>
            <#else>
                <label>
                    <input type="radio" name="q-${question.id}" value="${answer.name}"
                           <#if answer?index==0>checked</#if>>
                    ${answer.name}
                </label>
            <br>
            </#if>
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
             <button type="submit" class="btn btn-primary">
    <#if isEditForm>
        Сохранить тест
    <#else>
    Завершить тест
    </#if>
             </button>
         </div>
     </form>
 </div>
</#macro>
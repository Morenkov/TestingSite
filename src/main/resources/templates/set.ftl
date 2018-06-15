<#import "parts/common.ftl" as c>

<@c.page>
                 <form action="/makeTest/0/edit/none/go" method="get">
                     <span class="h3">Число вопросов</span>
                     <div class="input-group mb-3">
                         <select name="q" class="selectpicker mt-1">
                             <option value="3" selected>3</option>
                             <option value="5">5</option>
                             <option value="8">8</option>
                             <option value="10">10</option>
                             <option value="20">20</option>
                         </select>
                     </div>
                     <span class="h3">Число ответов в вопросе</span>
                     <div class="input-group mb-3">
                         <select name="a" class="selectpicker mt-1">
                             <option value="2" selected>2</option>
                             <option value="3">3</option>
                             <option value="4">4</option>
                             <option value="5">5</option>
                             <option value="6">6</option>
                         </select>
                     </div>
                     <div class="form-group">
                         <button type="submit" class="btn btn-primary">Создать тест</button>
                     </div>
                 </form>
</@c.page>

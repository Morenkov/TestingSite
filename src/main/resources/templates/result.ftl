<#import "parts/common.ftl" as c>

<@c.page>
  <div class="card">
      <div class="card-body">
          <h5 class="card-title">Результат прохождения теста: <b>${test.name}</b></h5>
          <p class="card-text">Ваш результат: ${result.points} баллов.</p>
      </div>
  </div>
</@c.page>

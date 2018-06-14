<div class="card-columns mt-4">
    <#list tests as test>

        <div class="card p-3 border-dark">
            <blockquote class="blockquote mb-0 card-body">
                <p>${test.name}</p>
                <i class="card-text"><span class="h6">Добавл/изменил:</span> ${test.author.username}</i>
                <footer class="footer">
                    <a href="/test/${test.id}" class="btn btn-primary">Начать</a>
                    <a href="/makeTest/${test.id}" class="btn btn-primary">Редактировать</a>
                </footer>
            </blockquote>
        </div>
    <#else>
        No tests
    </#list>
</div>

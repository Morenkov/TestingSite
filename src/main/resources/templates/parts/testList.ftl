<div class="card-columns">
    <#list tests as test>
        <div class="card my-3 border-dark" style="width: 36rem;">
            <div class="card-body m-2">
                <h5 class="card-title">${test.name}</h5>
                <i class="card-text">Создал: ${test.author.username}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-tests/${test.id}" class="btn btn-primary">Начать</a>
            </div>
        </div>

    <#else>
        No tests
    </#list>
</div>

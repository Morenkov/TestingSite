<#include "security.ftl">

<div class="card-columns">
    <#list tests as test>
        <div class="card my-3">
            <#if test.filename??>
                <img src="/img/${test.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${test.question}</span><br/>
                <i>#${test.tag}</i>
            </div>
            <div class="card-footer question-muted">
                <a href="/user-tests/${test.author.id}">${test.authorName}</a>
                <#if test.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-tests/${test.author.id}?message=${test.id}">
                        Edit
                    </a>
                </#if>
            </div>
        </div>
    <#else>
        No tests
    </#list>
</div>

<#include "security.ftl">

<div class="card-columns">
    <#list tests as message>
        <div class="card my-3">
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${message.question}</span><br/>
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer question-muted">
                <a href="/user-tests/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-tests/${message.author.id}?message=${message.id}">
                        Edit
                    </a>
                </#if>
            </div>
        </div>
    <#else>
        No message
    </#list>
</div>

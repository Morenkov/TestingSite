<#import "parts/common.ftl" as c>
<#import "parts/resultList" as r>
<#import "parts/userEdit.ftl" as u>

<@c.page>
    <#if user??>
        <@u.userEdit/>
    </#if>

<div class="card-columns">
    <@r.resultList false/>
</div>
</@c.page>
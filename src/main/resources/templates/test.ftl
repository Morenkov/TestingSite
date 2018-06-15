<#import "parts/common.ftl" as c>
<#import "parts/testRender.ftl" as t>

<@c.page>
    <@t.testRender "/test/${test.id}" false />
</@c.page>

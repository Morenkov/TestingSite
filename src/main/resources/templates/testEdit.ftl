<#import "parts/common.ftl" as c>
<#import "parts/testRender.ftl" as t>

<@c.page>
<h3>Test editor</h3>
    <@t.testRender "/makeTest/${test.id}" true />
</@c.page>


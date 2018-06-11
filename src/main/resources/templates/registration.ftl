<#import "parts/common.ftl" as c>
<#import "parts/loginReg.ftl" as l>

<@c.page "styleRegistration">
<div class="mb-1">Регистрация</div>
	${message?ifExists}
	<@l.login "/registration" true />
</@c.page>

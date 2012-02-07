
<#macro b_user_bar>

    <#if !userInfo.signed_in >
        <a href="/login">Войти</a>
    </#if>
    <#if userInfo.signed_in >
        <a href="/user/profile" title="Профиль пользователя">${userInfo.display_name}</a>
        &nbsp;
        <form action="/logout.logout" method="post" >
            <a href="/logout.logout" onclick="$(this).closest('form').submit(); return false;">
                Выйти
            </a>
        </form>
    </#if>

</#macro>
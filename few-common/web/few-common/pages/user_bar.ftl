<#if !userInfo.signed_in >
    <a href="/login">Войти</a>
</#if>
<#if userInfo.signed_in >
    <a href="/user_profile" title="Профиль пользователя">${userInfo.display_name}</a>
    &nbsp;
    <a href="/logout">Выйти</a>
</#if>
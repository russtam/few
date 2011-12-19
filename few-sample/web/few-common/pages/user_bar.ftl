<#if !userInfo.signedIn >
    <a href="/login">Войти</a>
</#if>
<#if userInfo.signedIn >
    <a href="/user_profile" title="Профиль пользователя">${userInfo.displayName}</a>
    &nbsp;
    <a href="/logout">Выйти</a>
</#if>
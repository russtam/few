
<#macro m_active_class uri>
<#escape x as x?trim>
    <#if request.requestURI.startsWith(uri)>
        b_menu__item__active
    </#if>
</#escape>
</#macro>

<#macro e_item href permission="">
    <#if permission?length == 0 || request.isUserInRole(permission) >
        <li class="b_menu__item <@m_active_class uri=href/>">
            <a href="${href}"><#nested/></a>
        </li>
    </#if>
</#macro>

<#macro b_menu permission="">
    <#if permission?length == 0 || request.isUserInRole(permission) >
        <nav class="b_menu">
            <ul>
                <#nested />
            </ul>
        </nav>
    </#if>
</#macro>


<#macro b_dispatcher>


    <table>
        <tr>
            <td valign="top" width="45%">
                <span style="font-weight: bold;">Действия</span>
                <@b_actions/>
            <td valign="top" >
                <span style="font-weight: bold;">Данные</span>
                <@b_beans/>
    </table>

</#macro>

<#macro b_actions>
    <table align="center">
        <thead>
            <tr style="background-color: #DFFFDF;">
                <td>
                <td>URI
                <td>Название
                <td>Разрешение
                <td>Список параметров
        </thead>
        <tbody>
            <#assign t = 0/>
            <#list actions as a>
                <#assign t = t + 1/>
            <tr style="background-color: #DFDFFF; vertical-align: top;">
                <td>${t}
                <td>${a.name}
                <td>${a.method_name }
                <td>${a.permission!""   }
                <td>
                    <#list a.parameters as p>
                        * ${p.name} <#if p.required>(required)</#if>: ${p.type} <br>
                    </#list>
            </#list>
        </tbody>
    </table>
</#macro>

<#macro b_beans>
    <table >
        <thead>
            <tr style="background-color: #DFFFDF;">
                <td>
                <td>Название модели
                <td>Разрешение
                <td>Параметры запроса
                <td>Имя класса
        </thead>
        <#assign t = 0/>
        <#list model_beans as m>
            <#assign t = t + 1/>
        <tr style="background-color: #DFDFFF; vertical-align: top;">
            <td>${t}
            <td>${m.name}
            <td>${m.permission!""}
            <td>
                <#list m.parameters as p>
                    * ${p.name} <#if p.required>(required)</#if>: ${p.type} <br>
                </#list>
            <td>${m.className}
        </#list>
    </table>
</#macro>
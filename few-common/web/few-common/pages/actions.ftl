
<#macro actions_marco>

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



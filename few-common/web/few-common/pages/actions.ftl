
<#macro actions_marco>

    <table align="center">
        <thead>
            <tr style="background-color: #DFFFDF;">
                <td>URI
                <td>Название
                <td>Разрешение
                <td>Список параметров
        </thead>
        <tbody>
            <#list actions as a>
            <tr style="background-color: #DFDFFF; vertical-align: top;">
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



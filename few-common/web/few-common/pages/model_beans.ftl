
<#macro model_beans_marco>

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
            <td>${m.permission}
            <td>
                <#list m.parameters as p>
                    * ${p.name} <#if p.required>(required)</#if>: ${p.type} <br>
                </#list>
            <td>${m.className}
        </#list>
    </table>

</#macro>



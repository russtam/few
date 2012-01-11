<#macro text l>
    <#switch l>
        <#case 0>minor<#break>
        <#case 1>info<#break>
        <#case 2>warning<#break>
        <#case 3>critical<#break>
    </#switch>
</#macro>


<#macro activity_log_macro>

    <style type="text/css">
        .minor {
            background-color: #e5ffe5;
        }
        .info {
            background-color: #e5e5ff;
        }
        .warning {
            background-color: #ffe8e8;
        }
        .critical {
            background-color: #fffff0;
            font-weight: bold;
            color: red;
        }
    </style>



    <form action="/audit?clear_activities" method="post">
        <input type="submit" value="Очистить">
    </form>


    <table>
        <thead>
            <tr>
                <td>№
                <td>пользователь
                <td>роль
                <td>важность
                <td>время
                <td>тип события
                <td>коммент
        </thead>
        <tbody>
    <#assign n = activity_log.size()/>
    <#list activity_log as l>
        <tr class="<@text l.level/>">
            <td>${n}
            <td>${l.display_name!""}(${l.user_id!""})
            <td>${l.role!""}
            <td><@text l.level/>
            <td>${l.time?string("HH:mm:ss dd-MM")}
            <td>${l.type}
            <td>${l.text}
        <#assign n = n - 1/>
    </#list>
    </table>

</#macro>

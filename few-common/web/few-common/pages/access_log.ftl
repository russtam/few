<#macro text l>
    <#if l < 400>
info
    <#elseif l < 500>
warning
    <#else>
critical
    </#if>
</#macro>

<#macro access_log_macro>
    <style type="text/css">
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

    <form action="/audit.clearAccessLog" method="post">
        <input type="submit" value="Очистить">
    </form>

    <table>
        <thead>
            <tr>
                <td>№
                <td>пользователь
                <td>запрос
                <td>ответ
                <td>от кого
                <td>от куда
                <td>время
        </thead>
        <tbody>
    <#assign n = access_log.size()/>
    <#list access_log as l>
        <tr class="<@text l.response_code/>">
            <td>${n}
            <td>${l.user_id!""}
            <td>${l.method} ${l.uri}
            <td>${l.response_code}
            <td>${l.remote_address}
            <td>${l.referer!""}
            <td>${l.time?string("HH:mm:ss dd-MM")}
        <#assign n = n - 1/>
    </#list>
    </table>
</#macro>

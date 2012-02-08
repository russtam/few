<#import "/blocks/common/b_messages/b_messages.ftl" as b_messages>
<#import "/blocks/common/b_form/b_form.ftl" as b_form>

<#macro f_level l>
    <#if l < 400>
info
    <#elseif l < 500>
warning
    <#else>
critical
    </#if>
</#macro>

<#macro b_access_log>
    <@b_form.b_form "/audit.clearAccessLog">
        <@b_form.e_submit "Очистить"/>
    </@b_form.b_form>

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
        <tr class="<@f_level l.response_code/>">
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
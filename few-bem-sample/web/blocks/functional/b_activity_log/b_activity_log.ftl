<#import "/blocks/common/b_messages/b_messages.ftl" as b_messages>
<#import "/blocks/common/b_form/b_form.ftl" as b_form>

<#macro f_level l>
    <#switch l>
        <#case 0>minor<#break>
        <#case 1>info<#break>
        <#case 2>warning<#break>
        <#case 3>critical<#break>
    </#switch>
</#macro>


<#macro b_activity_log>

    <@b_form.b_form "/audit.clearActivity">
        <@b_form.e_submit "Очистить"/>
    </@b_form.b_form>

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
        <tr class="<@f_level l.level/>">
            <td>${n}
            <td>${l.display_name!""}(${l.user_id!""})
            <td>${l.role!""}
            <td><@f_level l.level/>
            <td>${l.time?string("HH:mm:ss dd-MM")}
            <td>${l.type}
            <td>${l.text}
        <#assign n = n - 1/>
    </#list>
    </table>
</#macro>
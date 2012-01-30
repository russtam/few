<#include "messages.ftl">

<#macro status st>
    <#switch st>
        <#case 0>no activated<#break>
        <#case 1>active<#break>
        <#case 2>blocked<#break>
    </#switch>
</#macro>

<#macro user_list>

    <script type="text/javascript">
        $(document).ready(function() {
            $('#users_table').dataTable();
        } );
    </script>

    <a href="/admin/user_add">Добавить</a>

    <table id="users_table"  cellspacing="0" cellpadding="0" border="0" class="display">
        <thead>
            <tr>
                <td style="width: 100px">email</td>
                <td style="width: 100px">display name</td>
                <td style="width: 100px">status</td>
                <td style="width: 100px">role</td>
                <#list user_profile_fields as f>
                <#if f.displayInUserList>
                    <td><span title="${f.hint!""}">${f.displayName}</span>
                </#if>
                </#list>
            </tr>
        </thead>
        <tbody>
            <#list userList as u>
                <tr>
                    <td><a href="/admin/user_edit?user_id=${u.user_id?c}">${u.email}</a></td>
                    <td>${u.display_name}</td>
                    <td><@status u.status_id/></td>
                    <td>${u.display_role}</td>
                    <#list user_profile_fields as f>
                    <#if f.displayInUserList>
                        <td>${u.profile.get(f.field_id)!"--"}
                    </#if>
                    </#list>
                </tr>
            </#list>
        </tbody>
    </table>

</#macro>

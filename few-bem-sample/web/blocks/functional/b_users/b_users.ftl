<#import "/blocks/common/b_messages/b_messages.ftl" as b_messages>
<#import "/blocks/common/b_form/b_form.ftl" as b_form>
<#import "/blocks/common/b_containers/b_containers.ftl" as b_containers>

<#macro f_status st>
    <#switch st>
        <#case 0>no activated<#break>
        <#case 1>active<#break>
        <#case 2>blocked<#break>
    </#switch>
</#macro>

<#macro b_user_list>

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
                    <td><@f_status u.status_id/></td>
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



<#macro b_user_add>

    <a href="/admin/user_list"><< назад</a>

    <@b_form.b_form "/user_list.add">
        <@b_form.e_fields>
            <@b_form.e_field_text "Логин" "login"/>
            <@b_form.e_field_text "Имя" "display_name"/>
            <@b_form.e_field_text "E-mail" "email"/>
            <@b_form.e_field_checkbox "Активен" "active"/>
            <@b_form.e_field_combobox "Роль" "active" >
                <#list user_roles as r>
                    <@b_form.e_option r r r == "user"/>
                </#list>
            </@b_form.e_field_combobox>
            <#list user_profile_fields as f>
                <@b_form.e_field_text   f.displayName  f.field_id request.getParameter(f.field_id) f.hint f.required/>
            </#list>
        </@b_form.e_fields>

        <@b_containers.b_container_centered>
            <@b_form.e_submit "Добавить"/>
        </@b_containers.b_container_centered>
    </@b_form.b_form>

</#macro>




<#macro b_user_edit>
    <@b_messages.m_messages/>

    <a href="/admin/user_list"><< назад</a>

    <@b_form.b_form "/user_list.update?user_id=${user_profile.user_id?c}">
        <@b_form.e_fields>
            <@b_form.e_field_text "Логин" "login"/>
            <@b_form.e_field_text "Имя" "display_name"/>
            <@b_form.e_field_text "E-mail" "email"/>
            <@b_form.e_field_combobox "Статус" "status">
                <@b_form.e_option 0 "не активирован" user_profile.status == 0/>
                <@b_form.e_option 1 "активен" user_profile.status == 1/>
                <@b_form.e_option 2 "заблокирован" user_profile.status == 2/>
            </@b_form.e_field_combobox>
            <@b_form.e_field_combobox "Роль" "active" >
                <#list user_roles as r>
                    <@b_form.e_option r r r == "user"/>
                </#list>
            </@b_form.e_field_combobox>
            <#list user_profile_fields as f>
                <@b_form.e_field_text   f.displayName  f.field_id request.getParameter(f.field_id) f.hint f.required/>
            </#list>
        </@b_form.e_fields>
        <@b_containers.b_container_centered>
            <@b_form.e_submit "Обновить профиль"/>
        </@b_containers.b_container_centered>
    </@b_form.b_form>


    <#--<#if user_profile.login?exists>-->
    <#--<form action="/user_list.gen_pass?user_id=${user_profile.user_id?c}" method="POST">-->
        <#--<input type="submit" value="Выслать новый пароль">-->
    <#--</form>-->

    <#--<form action="/user_list.new_pass?user_id=${user_profile.user_id?c}" method="POST">-->
        <#--<input type="text" name="password">-->
        <#--<input type="submit" value="Задать новый пароль">-->
    <#--</form>-->
    <#--</#if>-->

    <#--<form action="/user_list.delete?user_id=${user_profile.user_id?c}" method="POST">-->
        <#--<input type="submit" value="Удалить">-->
    <#--</form>-->
    <#---->
    <#--<#if user_profile.status == 1>-->
        <#--<form action="/user_list.ban?user_id=${user_profile.user_id?c}" method="POST">-->
            <#--<input type="submit" value="Заблокировать">-->
        <#--</form>-->
    <#--<#else >-->
        <#--<form action="/user_list.unban?user_id=${user_profile.user_id?c}" method="POST">-->
            <#--<input type="submit" value="Активировать">-->
        <#--</form>-->
    <#--</#if>-->

</#macro>

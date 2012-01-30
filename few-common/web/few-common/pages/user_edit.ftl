<#include "messages.ftl">

<#macro status st>
    <#switch st>
        <#case 0>no activated<#break>
        <#case 1>active<#break>
        <#case 2>blocked<#break>
    </#switch>
</#macro>

<#macro user_edit>

    <@show_messages/>

    <a href="/admin/user_list"><< назад</a>
    <form action="/user_list.delete?user_id=${user_profile.user_id?c}" method="POST">
        <input type="submit" value="Удалить">
    </form>

    <#if user_profile.status == 1>
        <form action="/user_list.ban?user_id=${user_profile.user_id?c}" method="POST">
            <input type="submit" value="Заблокировать">
        </form>
    <#else >
        <form action="/user_list.unban?user_id=${user_profile.user_id?c}" method="POST">
            <input type="submit" value="Активировать">
        </form>
    </#if>


    <form action="/user_list.update?user_id=${user_profile.user_id?c}" method="POST">
        <table>
            <#if user_profile.login?exists>
            <tr>
                <td>login</td>
                <td><input type="text" name="login" value="${user_profile.login}"></td>
            </tr>
            </#if>
            <tr>
                <td>display name</td>
                <td><input type="text" name="display_name" value="${user_profile.display_name}"></td>
            </tr>
            <tr>
                <td>email</td>
                <td><input type="text" name="email" value="${user_profile.email}"></td>
            </tr>
            <tr>
                <td>status</td>
                <td>
                    <select name="status">
                        <option value="0" <#if user_profile.status == 0>selected=""</#if>>не активирован</option>
                        <option value="1" <#if user_profile.status == 1>selected=""</#if>>активен</option>
                        <option value="2" <#if user_profile.status == 2>selected=""</#if>>заблокирован</option>
                    </select>
            </tr>
            <tr>
                <td>role</td>
                <td>
                    <select name="display_role">
                        <#list user_roles as r>
                        <option value="${r}" <#if user_profile.display_role == r>selected=""</#if>>${r}</option>
                        </#list>
                    </select>
                </td>
            </tr>
        </table>

        <table>
            <#list user_profile_fields as f>
            <tr>
                <td><span title="${f.hint!""}">${f.displayName}</span>
                <td><input type="text" name="${f.field_id}" title="${f.hint!""}" placeholder="${f.hint!""}" value="${user_profile.field(f.field_id)!""}"/></td>
                <td><@common.show_messages source="${f.field_id}"/></td>
            </#list>
        </table>

        <input type="submit" value="Обновить профиль">
    </form>

    <#if user_profile.login?exists>
    <form action="/user_list.gen_pass?user_id=${user_profile.user_id?c}" method="POST">
        <input type="submit" value="Выслать новый пароль">
    </form>

    <form action="/user_list.new_pass?user_id=${user_profile.user_id?c}" method="POST">
        <input type="text" name="password">
        <input type="submit" value="Задать новый пароль">
    </form>
    </#if>

</#macro>

<#include "messages.ftl">

<#macro status st>
    <#switch st>
        <#case 0>no activated<#break>
        <#case 1>active<#break>
        <#case 2>blocked<#break>
    </#switch>
</#macro>

<#macro user_list_page>

    <#if !request.parameterMap.user_id?exists>
        <#if request.parameterMap.add_form?exists>
            <@add_form/>
        <#else>
            <@user_list_/>
        </#if>
    <#else>
        <#if user_profile?exists>
            <@user_profile_/>
        <#else>
            Нет такого пользователя
        </#if>
    </#if>

</#macro>

<#macro user_list_>

    <script type="text/javascript">
        $(document).ready(function() {
            $('#users_table').dataTable();
        } );
    </script>

    <a href="/user_list?add_form">Добавить</a>

    <table id="users_table"  cellspacing="0" cellpadding="0" border="0" class="display">
        <thead>
            <tr>
                <td style="width: 100px">email</td>
                <td style="width: 100px">display name</td>
                <td style="width: 100px">status</td>
                <td style="width: 100px">role</td>
            </tr>
        </thead>
        <tbody>
            <#list userList as u>
                <tr>
                    <td><a href="/user_list?user_id=${u.user_id?c}">${u.email}</a></td>
                    <td>${u.display_name}</td>
                    <td><@status u.status_id/></td>
                    <td>${u.display_role}</td>
                </tr>
            </#list>
        </tbody>
    </table>

</#macro>


<#macro user_profile_>

    <@show_messages/>

    <a href="/user_list"><< назад</a>
    <form action="/user_list?user_id=${user_profile.user_id?c}&delete" method="POST">
        <input type="submit" value="Удалить">
    </form>

    <#if user_profile.status == 1>
        <form action="/user_list?user_id=${user_profile.user_id?c}&ban" method="POST">
            <input type="submit" value="Заблокировать">
        </form>
    <#else >
        <form action="/user_list?user_id=${user_profile.user_id?c}&unban" method="POST">
            <input type="submit" value="Активировать">
        </form>
    </#if>


    <form action="/user_list?user_id=${user_profile.user_id?c}&update" method="POST">
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
    <form action="/user_list?user_id=${user_profile.user_id?c}&gen_pass" method="POST">
        <input type="submit" value="Выслать новый пароль">
    </form>

    <form action="/user_list?user_id=${user_profile.user_id?c}&new_pass" method="POST">
        <input type="text" name="password">
        <input type="submit" value="Задать новый пароль">
    </form>
    </#if>

</#macro>

<#macro add_form>
    <a href="/user_list"><< назад</a>

    <form action="/user_list?add" method="POST">
        <table>
            <tr>
                <td>login</td>
                <td><input type="text" name="login"></td>
            </tr>
            <tr>
                <td>display name</td>
                <td><input type="text" name="display_name""></td>
            </tr>
            <tr>
                <td>email</td>
                <td><input type="text" name="email""></td>
            </tr>
            <tr>
                <td>active</td>
                <td>
                    <input type="checkbox" checked="" name="active">
            </tr>
            <tr>
                <td>role</td>
                <td>
                    <select name="display_role">
                        <#list user_roles as r>
                        <option value="${r}" <#if r == "user">selected=""</#if>>${r}</option>
                        </#list>
                    </select>
                </td>
            </tr>
        </table>

        <input type="submit" value="Добавить">
    </form>
</#macro>
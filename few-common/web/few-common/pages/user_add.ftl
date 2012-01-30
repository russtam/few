<#include "messages.ftl">

<#macro user_add>
    <a href="/admin/user_list"><< назад</a>

    <form action="/user_list.add" method="POST">
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
<#include "messages.ftl">

<#macro user_profile>

    <@common.show_messages/>

    <#if userInfo.signed_in>
        Здравствуйте ${userInfo.display_name}! <br/>

        Ваш логин: ${userInfo.login} <br/>
        <br/>

        <@common.show_messages source="email"/>
        <form action="/user_profile" method="POST">
            <table>
                <tr>
                    <td>Ваш e-mail: </td>
                    <td><input type="text" name="email" value="${userInfo.email}"/></td>
                </tr>
            </table>
            <input type="submit" value="Сменить e-mail">
        </form>

        <@common.show_messages source="display_name"/>
        <form action="/user_profile" method="POST">
            <table>
                <tr>
                    <td>Ваше имя: </td>
                    <td><input type="text" name="name" value="${userInfo.display_name}"/></td>
                </tr>
            </table>
            <input type="submit" value="Сменить отображаемое имя">
        </form>

        <@common.show_messages source="password"/>
        <form action="/user_profile" method="post" autocomplete="off">
            <table>
                <tr>
                    <td>Старый пароль: </td>
                    <td><input type="password" name="old_password" /></td>
                    <td><@common.show_messages source="old_password"/></td>
                <tr>
                    <td>Новый пароль: </td>
                    <td><input type="password" name="password" /></td>
                <tr>
                    <td>Повторите новый пароль: </td>
                    <td><input type="password" name="password1" /></td>
                </tr>
            </table>
            <input type="submit" value="Сменить пароль">
        </form>

        <form action="/user_profile?profile" method="post" autocomplete="off">
            <table>
                <#list user_profile_fields as f>
                <tr>
                    <td><span title="${f.hint!""}">${f.displayName}</span>
                    <td><input type="text" name="${f.field_id}" title="${f.hint!""}" placeholder="${f.hint!""}" value="${userInfo.field(f.field_id)!""}"/></td>
                    <td><@common.show_messages source="${f.field_id}"/></td>
                </#list>
            </table>
            <input type="submit" value="Обновить профиль">
        </form>

    </#if>

</#macro>

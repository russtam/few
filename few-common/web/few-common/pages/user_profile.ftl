<#include "message.ftl">

<#macro user_profile>

    <@common.show_messages source="all"/>

    <#if userInfo.signedIn>
        Здравствуйте ${userInfo.displayName}! <br/>

        Ваш логин: ${fullUserInfo.login} <br/>
        Ваш e-mail: ${fullUserInfo.email} <br/>
        <br/>
        <@common.show_messages source="drgn"/>

        <@common.show_messages source="display_name"/>
        <form action="/user_profile" method="POST">
            <table>
                <tr>
                    <td>Ваше имя: </td>
                    <td><input type="text" name="name" value="${userInfo.displayName}"/></td>
                </tr>
            </table>
            <input type="submit" value="Сменить отображаемое имя">
        </form>
        <br/>

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


    </#if>

</#macro>

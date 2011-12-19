<#include "message.ftl">

<#macro login>

    <form action="${loginForm.loginUrl}" method="POST">

        <@common.show_messages source="all"/>
        <#if loginForm.errorKey?exists  >
            <div style="color: red;">
                <#if loginForm.errorKey == 1>
                    Неправильный логин или пароль
                </#if>
                <#if loginForm.errorKey == 2>
                    Аккаунт не активирован. Проверьте почту.
                </#if>
                <#if loginForm.errorKey == 3>
                    Пользователь заблокирован. Обратитесь к администратору сайта.
                </#if>
            </div>
        </#if>

        <table>
            <tr>
                <td>Логин: </td>
                <td><input type="text" name="login"/></td>
            </tr>
            <tr>
                <td>Пароль: </td>
                <td><input type="password" name="password"/></td>
            </tr>
        </table>

        <input type="submit" value="Войти"/>

        <br/>
        <a href="/registration">Зарегистрироваться</a> &nbsp; <br/>
        <a href="/restore_password">Восстановить пароль</a> &nbsp; <br/>

    </form>

</#macro>



<#include "messages.ftl">

<#macro login>

    <form action="${loginForm.loginUrl}" method="POST">

        <@common.show_messages/>
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



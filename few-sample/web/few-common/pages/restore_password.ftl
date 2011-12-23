<#include "messages.ftl">

<#macro restore_password>

    <@common.messages/>

    <form action="/restore_password" method="POST" autocomplete="off">


        <table>
            <tr>
                <td>Введите email: </td>
                <td><input type="text" name="email"/></td>
            </tr>
        </table>

        <input type="submit" value="Выслать новый пароль..."/>

    </form>

</#macro>



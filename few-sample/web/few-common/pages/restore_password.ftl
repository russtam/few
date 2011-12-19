<#include "message.ftl">

<#macro restore_password>

    <@common.show_messages source="all"/>

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



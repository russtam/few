<#include "messages.ftl">

<#macro restore_password>

    <@common.show_messages/>

    <form action="/restore_password" method="POST" >


        <table>
            <tr>
                <td>Введите email: </td>
                <td><input type="text" name="email"/></td>
            </tr>
        </table>

        <input type="submit" value="Выслать новый пароль..."/>

    </form>

</#macro>



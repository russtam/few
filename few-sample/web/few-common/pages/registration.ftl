<#include "messages.ftl">

<#macro registration>

    У нас очень простая регистрация. <br/>
    Вы можете ввести только свой e-mail и нажать зарегистрироваться, в этом случае система вышлет на email пароль для входа в систему. <br/>
    Вы так же можете указать свой любимый пароль, тогда система запомнит его, а на email будет выслана ссылка для активации аккаунта. <br/>
    Пароли храняться в системе в зашифрованном виде, поэтому кража паролей невозможна, так же как и их восстановление. <br/>
    При необходимости восстановить утерянный пароль, мы вышлем вам на email новый пароль.<br/>
    Вы так же можете указать короткий логин для входа в систему, иначе в качестве логина будет использован email <br/>

    <form action="/registration" method="POST" autocomplete="off">

        <table>
            <tr>
                <td style="font-weight: bold;">Введите e-mail: </td>
                <td>
                    <#if request.parameterMap.email?exists>
                        <#assign email = request.parameterMap.email[0]>
                    <#else >
                        <#assign email = "">
                    </#if>

                    <input type="text" name="email" value="${email}"/>
                    <span style="color: red;font-weight: bold;">*</span> </td>
                <td><@common.messages source="email"/></td>
            <tr>
                <td>Введите логин: </td>
                <td>
                    <#if request.parameterMap.login?exists>
                        <#assign login = request.parameterMap.login[0]>
                    <#else >
                        <#assign login = "">
                    </#if>
                    <input type="text" name="login" value="${login}"/>
                </td>
                <td><@common.messages source="login"/></td>
            <tr>
                <td>Введите ваше имя: </td>
                <td>
                    <#if request.parameterMap.name?exists>
                        <#assign name = request.parameterMap.name[0]>
                    <#else >
                        <#assign name = "">
                    </#if>
                    <input type="text" name="name" value="${name}"/>
                </td>
                <td><@common.messages source="name"/></td>
            <tr>
                <td>Введите пароль: </td>
                <td><input type="password" name="password"/></td>
            <tr>
                <td>Повторите пароль: </td>
                <td><input type="password" name="password1"/></td>
                <td><@common.messages source="password"/></td>
            </tr>
        </table>

        <input type="hidden" name="registration"/>
        <input type="submit" value="Зарегистрироваться"/>

    </form>


</#macro>



<#import "/blocks/common/b_messages/b_messages.ftl" as b_messages>
<#import "/blocks/common/b_form/b_form.ftl" as b_form>
<#import "/blocks/common/b_containers/b_containers.ftl" as b_containers>

<#macro b_login>

    <@b_messages.m_messages/>

    <@b_form.b_form loginForm.loginUrl>
        <@b_form.e_fields>
            <@b_form.e_field_text       "Логин"  "login"/>
            <@b_form.e_field_password   "Пароль" "password"/>
        </@b_form.e_fields>

        <@b_containers.b_container_centered>
            <@b_form.e_submit "Войти"/>
        </@b_containers.b_container_centered>

    </@b_form.b_form>

    <@b_containers.b_container_centered>
        <p class="b_login__href_register" > <a href="/registration">Зарегистрироваться</a> </p>
        <p class="b_login__href_restore" > <a href="/restore_password">Восстановить пароль</a> </p>
    </@b_containers.b_container_centered>


</#macro>
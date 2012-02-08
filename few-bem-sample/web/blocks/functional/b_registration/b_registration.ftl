<#import "/blocks/common/b_messages/b_messages.ftl" as b_messages>
<#import "/blocks/common/b_form/b_form.ftl" as b_form>
<#import "/blocks/common/b_containers/b_containers.ftl" as b_containers>

<#macro b_registration>

    <@b_messages.m_messages/>

    <@b_form.b_form "/registration" "registration" "off">
        <@b_form.e_fields>
            <@b_form.e_field_text       "Введите e-mail"  "email" request.getParameter("email") "" true/>

            <@b_form.e_field_text       "Введите логин"   "login" request.getParameter("login") "" false/>

            <@b_form.e_field_password   "Введите пароль"  "password" "" "" false/>

            <@b_form.e_field_password   "Повторите пароль"  "password1" "" "" false/>

            <#list user_profile_fields as f>
            <#if f.displayInProfile>
                <@b_form.e_field_text   f.displayName  f.field_id request.getParameter(f.field_id) f.hint f.required/>
            </#if>
            </#list>

        </@b_form.e_fields>

        <@b_containers.b_container_centered>
            <@b_form.e_submit "Зарегистрироваться"/>
        </@b_containers.b_container_centered>

    </@b_form.b_form>



</#macro>
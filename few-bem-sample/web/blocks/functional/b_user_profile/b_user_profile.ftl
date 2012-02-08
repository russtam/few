<#import "/blocks/common/b_messages/b_messages.ftl" as b_messages>
<#import "/blocks/common/b_form/b_form.ftl" as b_form>
<#import "/blocks/common/b_containers/b_containers.ftl" as b_containers>

<#macro b_user_profile>

    Здравствуйте ${userInfo.display_name}! <br/>
    Ваш логин: ${userInfo.login} <br/>
    <br/>

    <@b_messages.m_messages/>

    <#assign class="user_profile"/>
    <@b_form.b_form "/user_profile.updateProfile" "${class}">
        <@b_form.e_fields>
            <@b_form.e_field_text "Ваше имя" "name" userInfo.display_name/>
            <#list user_profile_fields as f>
            <#if f.displayInProfile>
                <@b_form.e_field_text   f.displayName  f.field_id userInfo.profile.get(f.field_id)!"" f.hint f.required/>
            </#if>
            </#list>
        </@b_form.e_fields>

        <@b_containers.b_container_centered>
            <@b_form.e_submit "Обновить профиль"/>
        </@b_containers.b_container_centered>
    </@b_form.b_form>

    <@b_form.b_form "/user_profile.changePassword" "${class}" "off">
        <@b_form.e_fields>
            <@b_form.e_field_password "Старый пароль"          "old_password" />
            <@b_form.e_field_password "Новый пароль"           "password" />
            <@b_form.e_field_password "Повторите новый пароль" "password1" />
        </@b_form.e_fields>

        <@b_containers.b_container_centered>
            <@b_form.e_submit "Сменить пароль"/>
        </@b_containers.b_container_centered>
    </@b_form.b_form>

    <@b_form.b_form "/user_profile.changeEMail" "${class}" >
        <@b_form.e_fields>
            <@b_form.e_field_text "Ваш e-mail" "email" userInfo.email/>
        </@b_form.e_fields>

        <@b_containers.b_container_centered>
            <@b_form.e_submit "Сменить e-mail"/>
        </@b_containers.b_container_centered>
    </@b_form.b_form>



</#macro>
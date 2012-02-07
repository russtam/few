<#import "/blocks/common/b_messages/b_messages.ftl" as b_messages>
<#import "/blocks/common/b_form/b_form.ftl" as b_form>
<#import "/blocks/common/b_containers/b_containers.ftl" as b_containers>


<#macro b_restore_password>

    <@b_messages.m_messages/>

    <@b_form.b_form "/restore_password.restore">
        <@b_form.e_fields>
            <@b_form.e_field_text       "Введите email"  "email"/>
        </@b_form.e_fields>

        <@b_containers.b_container_centered>
            <@b_form.e_submit "Выслать новый пароль"/>
        </@b_containers.b_container_centered>

    </@b_form.b_form>


</#macro>
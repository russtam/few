<#import "/blocks/common/b_page/b_page.ftl" as b_page>
<#import "/blocks/common/b_center_layout/b_center_layout.ftl" as b_center_layout>
<#import "/blocks/functional/b_restore_password/b_restore_password.ftl" as b_restore_password>

<#assign title="Восстановление пароля"/>

<@b_page.b_page title>

        <@b_center_layout.b_center_layout>

            <@b_restore_password.b_restore_password/>

        </@b_center_layout.b_center_layout>

</@b_page.b_page>
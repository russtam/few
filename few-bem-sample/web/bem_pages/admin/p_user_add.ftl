<#import "/blocks/common/b_page/b_page.ftl" as b_page>
<#import "/blocks/common/b_center_layout/b_center_layout.ftl" as b_center_layout>
<#import "/blocks/functional/b_users/b_users.ftl" as b_users>

<#assign title="Список пользователей >> Добавление"/>

<@b_page.b_page title>

        <@b_center_layout.b_center_layout>

            <@b_users.b_user_add/>

        </@b_center_layout.b_center_layout>

</@b_page.b_page>


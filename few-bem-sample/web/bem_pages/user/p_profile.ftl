<#import "/blocks/common/b_page/b_page.ftl" as b_page>
<#import "/blocks/common/b_center_layout/b_center_layout.ftl" as b_center_layout>
<#import "/blocks/functional/b_user_profile/b_user_profile.ftl" as b_user_profile>

<#assign title="Профиль пользователя"/>

<@b_page.b_page title>

        <@b_center_layout.b_center_layout>

            <@b_user_profile.b_user_profile/>

        </@b_center_layout.b_center_layout>

</@b_page.b_page>
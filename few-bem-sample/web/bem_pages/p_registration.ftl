<#import "/blocks/common/b_page/b_page.ftl" as b_page>
<#import "/blocks/common/b_center_layout/b_center_layout.ftl" as b_center_layout>
<#import "/blocks/functional/b_registration/b_registration.ftl" as b_registration>

<#assign title="Регистрация"/>

<@b_page.b_page title>

        <@b_center_layout.b_center_layout>

            <@b_registration.b_registration/>

        </@b_center_layout.b_center_layout>

</@b_page.b_page>
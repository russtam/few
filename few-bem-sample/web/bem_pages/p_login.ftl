<#import "/blocks/common/b_page/b_page.ftl" as b_page>
<#import "/blocks/common/b_center_layout/b_center_layout.ftl" as b_center_layout>
<#import "/blocks/functional/b_login/b_login.ftl" as b_login>

<#assign title="Логин"/>

<@b_page.b_page title>

        <@b_center_layout.b_center_layout>

            <@b_login.b_login/>

        </@b_center_layout.b_center_layout>

</@b_page.b_page>
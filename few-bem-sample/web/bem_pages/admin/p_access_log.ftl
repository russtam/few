<#import "/blocks/common/b_page/b_page.ftl" as b_page>
<#import "/blocks/common/b_center_layout/b_center_layout.ftl" as b_center_layout>
<#import "/blocks/functional/b_access_log/b_access_log.ftl" as b_access_log>

<#assign title="AccessLog"/>

<@b_page.b_page title>

        <@b_center_layout.b_center_layout>

            <@b_access_log.b_access_log/>

        </@b_center_layout.b_center_layout>

</@b_page.b_page>


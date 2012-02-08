<#import "/blocks/common/b_page/b_page.ftl" as b_page>
<#import "/blocks/common/b_center_layout/b_center_layout.ftl" as b_center_layout>
<#import "/blocks/functional/b_activity_log/b_activity_log.ftl" as b_activity_log>

<#assign title="ActivityLog"/>

<@b_page.b_page title>

        <@b_center_layout.b_center_layout>

            <@b_activity_log.b_activity_log/>

        </@b_center_layout.b_center_layout>

</@b_page.b_page>


<#import "/blocks/common/b_page/b_page.ftl" as b_page>
<#import "/blocks/common/b_center_layout/b_center_layout.ftl" as b_center_layout>
<#import "/blocks/functional/b_dispatcher/b_dispatcher.ftl" as b_dispatcher>

<#assign title="FewDispatcher"/>

<@b_page.b_page title>

        <@b_center_layout.b_center_layout>

            <@b_dispatcher.b_dispatcher/>

        </@b_center_layout.b_center_layout>

</@b_page.b_page>


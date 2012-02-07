<#macro m_messages source="all">

    <#if messages.containsKey("${source}")>

        <@b_messages messages.get("${source}")>
        </@b_messages>

    </#if>

</#macro>

<#-- receive list of few.Message for specified source -->
<#macro b_messages messages>
    <div class="b_messages">
        <#list messages as m>
            <@b_message m/>
        </#list>
    </div>
</#macro>


<#macro b_message m>
    <#--  make class modificator: info/warning/error  -->
    <#assign modificator=m.severity?lower_case/>

    <div class="b_message b_message__severity__${modificator}">
        ${m.message}
    </div>

</#macro>
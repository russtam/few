<#macro show_messages source="all">

    <#if messages.containsKey("${source}")>

        <#assign list = messages.get("${source}")>
        <#list list as m>

            <#switch m.severity>
                <#case "INFO">
                    <div style="color: blue;">
                        ${m.message}
                    </div>
                <#break>
                <#case "WARNING">
                    <div style="color: #daa520;">
                        ${m.message}
                    </div>
                <#break>
                <#case "ERROR">
                    <div style="color: #8b0000;font-weight: bold;">
                        ${m.message}
                    </div>
                <#break>
            </#switch>
        </#list>
    </#if>

</#macro>
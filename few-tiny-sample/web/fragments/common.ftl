<#--        THIS IS THE COMMON PAGE TEMPLATE        -->
<#--        LOAD ALL COMMON MACROS HERE             -->
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


<#macro page title>
<html>
<head>
    <title>${title?html}</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>

</head>
<body>

<div class="wrapper">
    <div class="text">
    <table border="0" cellspacing=0 cellpadding=0 width="100%">
        <tr valign="middle">
            <td align="left">
                <h1 >
                ${title?html}
                </h1>
            </td>
            <td align="right">
                UserBar
            </td>
        </tr>
    </table>

    <hr/>
        Navigation
    <hr>
    </div>

    <table style="margin-left:auto;margin-right:auto;height: 70%;">
        <tr>
            <td style="vertical-align: middle;">
                <div>
                    <#nested>
                </div>
            </td>
        </tr>
    </table>


</div>

<div class="footer">
    <hr/> <br/>
    <table border="0" cellspacing=0 cellpadding=0 width="100%">
        <tr valign="middle">
            <td align="left">
                <i>project</i>
            </td>
            <td align="right">
                (c)
            </td>
        </tr>
    </table>
</div>
</body>
</html>
</#macro>
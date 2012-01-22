<#--        THIS IS THE COMMON PAGE TEMPLATE        -->
<#--        LOAD ALL COMMON MACROS HERE             -->
<#include "../few-common/pages/messages.ftl">


<#macro page title>
<html>
<head>
    <title>${title?html}</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript" src="/static/jquery.js"></script>
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
                <#include "../few-common/pages/user_bar.ftl">
            </td>
        </tr>
    </table>

    <hr/>
        <#if userInfo.isUserInRole("admin")>
            <a href="/user_list">user_list</a>
            <a href="/dispatcher">dispatcher</a>
            <a href="/activity_log">activity_log</a>
            <a href="/access_log">access_log</a>
        <#else >
            Navigation
        </#if>
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
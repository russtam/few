<#--        THIS IS THE COMMON PAGE TEMPLATE        -->
<#--        LOAD ALL COMMON MACROS HERE             -->
<#include "../few-common/pages/message.ftl">


<#macro page title>
<html>
<head>
    <title>${title?html}</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/static/css/common.css" />
    <script type="text/javascript" src="/static/js/jquery-1.7.js"></script>
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
        <#--<#include "navigation_top.ftl">-->
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
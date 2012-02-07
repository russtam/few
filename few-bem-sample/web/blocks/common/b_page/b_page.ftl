<#import "/blocks/common/b_header/b_header.ftl" as b_header>
<#import "/blocks/common/b_footer/b_footer.ftl" as b_footer>

<#macro b_page title>
<!DOCTYPE html> <#-- html 5 doctype -->
<html>
<head>
    <title>${title?html}</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>

    <script type="text/javascript" src="/static/js/jquery.js"></script>

    <link rel="stylesheet" href="/static/css/main.css" />

</head>
<body>

    <div class="b_page__container">

    <@b_header.b_header title />

            <#nested />

    <@b_footer.b_footer/>

    </div>

</body>
</html>
</#macro>
<#import "/blocks/common/b_header/b_header.ftl" as b_header>
<#import "/blocks/common/b_footer/b_footer.ftl" as b_footer>

<#macro b_page title>
<!DOCTYPE html> <#-- html 5 doctype -->
<html>
<head>
    <title>${title?html}</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>

    <script type="text/javascript" src="/static/js/jquery.js"></script>

    <link rel="stylesheet" href="/static/css/reset.css" />

    <link rel="stylesheet" href="/blocks/common/b_center_layout/b_center_layout.css" />
    <link rel="stylesheet" href="/blocks/common/b_footer/b_footer.css" />
    <link rel="stylesheet" href="/blocks/common/b_header/b_header.css" />
    <link rel="stylesheet" href="/blocks/common/b_messages/b_messages.css" />
    <link rel="stylesheet" href="/blocks/common/b_form/b_form.css" />
    <link rel="stylesheet" href="/blocks/common/b_containers/b_containers.css" />
    <link rel="stylesheet" href="/blocks/functional/b_login/b_login.css" />


    <link rel="stylesheet" href="/blocks/common/b_page/b_page.css" />

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
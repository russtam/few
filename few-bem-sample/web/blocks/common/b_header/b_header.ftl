<#import "../b_menu/b_menu.ftl" as b_menu>
<#import "../../functional/b_user_bar/b_user_bar.ftl" as b_user_bar>

<#macro b_header title>

    <header class="b_header">
        <div class="b_header__top">
            <div class="b_header__logo">
                FEW Sample Application
            </div>
            <div class="b_header__user_bar">
                <@b_user_bar.b_user_bar/>
            </div>
        </div>

        <div class="b_header__user_menu">
            <@b_menu.b_menu permission="user">
                <@b_menu.e_item href="/user/profile">Профиль</@b_menu.e_item>
            </@b_menu.b_menu>
        </div>

        <div class="b_header__admin_menu">
            <@b_menu.b_menu permission="admin">
                <@b_menu.e_item href="/admin/user_list">Пользователи</@b_menu.e_item>
                <@b_menu.e_item href="/admin/activity_log">Лог активностей</@b_menu.e_item>
                <@b_menu.e_item href="/admin/access_log">Лог запросов</@b_menu.e_item>
                <@b_menu.e_item href="/admin/dispatcher">Конфигурация движка</@b_menu.e_item>
            </@b_menu.b_menu>
        </div>

        <div class="b_header__breadcrumbs">
            >> ${title}
        </div>
    </header>

</#macro>
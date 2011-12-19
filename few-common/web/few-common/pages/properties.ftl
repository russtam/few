<#include "message.ftl">

<#macro new_row>
    <tr id="prop_">
        <td>
            <input type="text" name="" value="" onkeyup="change_name(this)">
        </td>
        <td>
            <input type="text" name="prop_" value="">
        </td>
        <td>
            <a href="/static/html/noJS.html" onclick="remove_row(\'prop_\'); return false;">Удалить</a>
        </td>
    </tr>
</#macro>

<#macro properties>

    <#assign new_row>
        <@new_row/>
    </#assign>

    <script type="text/javascript">
        function change_name(el) {
            var value_field = el.parentNode.nextSibling.nextSibling.firstChild.nextSibling;
            value_field.name = "prop_" + el.value;
        }
        function remove_row(id) {
            $('#' + id).remove();
        }
        function add_row() {
            $('#table').append('${new_row?js_string}');
        }

    </script>

    <b>Настройки системы</b>

    <form action="/properties" method="post" >

        <table id="table">
            <#list systemProperties.entrySet() as p>

            <tr id="prop_${p.getKey()}">
                <td><span title="${p.getValue().getDescription()}">${p.getKey()}</span> </td>
                <td>
                    <input type="text" name="prop_${p.getKey()}" title="${p.getValue().getDescription()}" value="${p.getValue().getString()}">
                </td>
                <td>
                    <a href="/static/html/noJS.html" onclick="remove_row('prop_${p.getKey()}'); return false;">Удалить</a>
                </td>
            </tr>

            </#list>
        </table>
        <a href="/static/html/noJS.html" onclick="add_row(); return false;">Добавить</a>
        <br/>
        <input type="submit" name="save" value="Сохранить">
        <input type="submit" name="clear" value="Отменить">

    </form>

</#macro>


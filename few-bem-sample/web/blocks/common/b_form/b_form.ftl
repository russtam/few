<#import "/blocks/common/b_messages/b_messages.ftl" as b_messages>

<#macro e_submit text>
    <input type="submit" value="${text}" class="b_form__button"/>
</#macro>


<#macro e_fields>
    <table class="b_form__table">
        <#nested>
    </table>
</#macro>

<#macro f_field_tr id title name hint required>
    <#if required>
        <#assign required_modificator="_required"/>
    <#else>
        <#assign required_modificator="_optional"/>
    </#if>
    <tr class="b_form__row ">
        <td class="b_form__label_col b_form__label_col${required_modificator}">
            <label for="${id}" title="${hint}" class="b_form__label b_form__label${required_modificator!""}">${title}:</label>
        </td>
        <td class="b_form__input_col">
            <#nested >
        </td>
        <td class="b_form__message_col">
            <@b_messages.m_messages name/>
        </td>
    </tr>
</#macro>

<#macro e_field_text title name value="" hint="" required=true>
    <#assign id = "r_" + new_id()>
    <@f_field_tr id title name hint required>
        <input type="text" name="${name}" id="${id}" value="${value}" class="b_form__input" placeholder="${hint}"/>
    </@f_field_tr>
</#macro>

<#macro e_field_checkbox title name value=false hint="" required=true>
    <#assign id = "r_" + new_id()>
    <@f_field_tr id title name hint required>
        <input type="checkbox" name="${name}" id="${id}" class="b_form__input" <#if value>checked=""></#if>/>
    </@f_field_tr>
</#macro>

<#macro e_field_combobox title name value="" hint="" required=true>
    <#assign id = "r_" + new_id()>
    <@f_field_tr id title name hint required>
        <select name="${name}" id="${id}" class="b_form__input" >
            <#nested/>
        </select>
    </@f_field_tr>
</#macro>

<#macro e_option value title selected>
    <option value="${value}" <#if selected>selected=""</#if>>${title}</option>
</#macro>

<#macro e_field_password title name value="" hint="" required=true>
    <#assign id = new_id()>
    <@f_field_tr id title name hint required>
        <input type="password" name="${name}" id="${id}" value="${value}" class="b_form__input"/>
    </@f_field_tr>
</#macro>

<#macro b_form action class="" autocomplete="on" >
    <#if class?length != 0 >
        <#assign modificator="b_form__${class}"/>
    </#if>
    <form action="${action}" method="post" class="b_form ${modificator!""}"
            autocomplete="${autocomplete}">
        <#nested >
    </form>

</#macro>
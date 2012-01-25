<#import "../../fragments/common.ftl" as common>
<#import "../../few-common/pages/model_beans.ftl" as mb>
<#import "../../few-common/pages/actions.ftl" as ac>

<@common.page title="FewDispatcher">

    <table>
        <tr>
            <td valign="top" width="45%">
                <span style="font-weight: bold;">Действия</span>
                <@ac.actions_marco/>
            <td valign="top" >
                <span style="font-weight: bold;">Данные</span>
                <@mb.model_beans_marco/>
    </table>

</@common.page>



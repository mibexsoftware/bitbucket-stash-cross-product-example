define('plugin/stash', [
       'jquery',
       'stash/api/util/state',
       'plugin/openTaskResolver',
       'exports'
    ],

    function ($, state, openTaskResolver, exports) {
        'use strict';

        function bindTasksResolveButton() {
            $('.resolve-open-tasks').mousedown(function (e) {
                openTaskResolver.resolveTasks(state);
            });
        }

        exports.onReady = function () {
            bindTasksResolveButton();
        };
    }
);

AJS.$(function () {
    require('plugin/stash').onReady();
});
define('plugin/bitbucket', [
       'jquery',
       'bitbucket/util/state',
       'plugin/openTaskResolver',
       'exports'
    ],

    function ($, state, closeOpenTasksHandler, exports) {
        'use strict';

        function bindTasksResolveButton() {
            $('.resolve-open-tasks').mousedown(function (e) {
                closeOpenTasksHandler.resolveTasks(state);
            });
        }

        exports.onReady = function () {
            bindTasksResolveButton();
        };
    }
);

AJS.$(function () {
    require('plugin/bitbucket').onReady();
});
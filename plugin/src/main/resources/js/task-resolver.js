define('plugin/openTaskResolver', [
       'jquery',
       'underscore',
       'aui/flag',
       'exports'
    ],

    function ($, _, flag, exports) {
        'use strict';

        function resolve(tasks) {
            var taskUrl = AJS.contextPath() + '/rest/api/1.0/tasks';
            var resolveRequests = _.map(tasks, function (task) {
                return $.ajax({
                    url: taskUrl + '/' + task.id,
                    type: 'PUT',
                    data: JSON.stringify({state: 'RESOLVED'}),
                    contentType: 'application/json'
                });
            });
            $.when(resolveRequests)
                .then(function () {
                    flag({
                        type: 'success',
                        body: 'All open tasks are resolved now.',
                        close: 'auto'
                    });
                })
                .fail(function () {
                    flag({
                        type: 'error',
                        body: 'Error occurred while resolved open tasks.',
                        close: 'manual'
                    });
                });
        }

        function findTasksInPullRequest(state) {
            var projectKey = state.getProject().key;
            var repoSlug = state.getRepository().slug;
            var pullRequestId = state.getPullRequest().id;
            var allTasksUrl = AJS.contextPath() + '/rest/api/1.0/projects/' +
                projectKey + '/repos/' + repoSlug + '/pull-requests/' + pullRequestId + '/tasks';
            return $.getJSON(allTasksUrl)
        }

        exports.resolveTasks = function (state) {
            findTasksInPullRequest(state).done(function (data) {
                var openTasks = _.filter(data.values, function (task) {
                    return task.state === 'OPEN';
                });
                resolve(openTasks)
            });
        };
    }
);
package ch.mibex.crossproduct.bitbucket

import ch.mibex.crossproduct.api.OpenTasksCounter
import com.atlassian.bitbucket.pull.{PullRequest, PullRequestService, PullRequestTaskSearchRequest}
import com.atlassian.bitbucket.task.{Task, TaskState}
import com.atlassian.bitbucket.util.{PageProvider, PageRequest, PageUtils, PagedIterable}

import scala.collection.JavaConverters._


class BitbucketOpenTasksCounter(pullRequestService: PullRequestService) extends OpenTasksCounter[PullRequest] {

  override def countOpenTasks(pullRequest: PullRequest): Long = {
    val searchRequest = new PullRequestTaskSearchRequest.Builder(pullRequest).build()
    new PagedIterable[Task](new PageProvider[Task] {

      override def get(pageRequest: PageRequest) =
        pullRequestService.searchTasks(searchRequest, pageRequest)

    }, PageUtils.newRequest(0, 200))
      .asScala
      .count(_.getState == TaskState.OPEN)
  }

}

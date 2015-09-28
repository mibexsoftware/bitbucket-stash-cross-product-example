package ch.mibex.crossproduct.stash

import ch.mibex.crossproduct.api.OpenTasksCounter
import com.atlassian.stash.pull.{PullRequest, PullRequestService, PullRequestTaskSearchRequest}
import com.atlassian.stash.task.{Task, TaskState}
import com.atlassian.stash.util.{PageProvider, PageRequest, PageUtils, PagedIterable}

import scala.collection.JavaConverters._

class StashOpenTasksCounter(pullRequestService: PullRequestService) extends OpenTasksCounter[PullRequest] {

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

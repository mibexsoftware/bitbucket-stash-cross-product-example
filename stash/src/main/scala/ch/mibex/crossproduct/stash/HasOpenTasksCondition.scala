package ch.mibex.crossproduct.stash

import java.util.{Map => JMap}

import ch.mibex.crossproduct.api.OpenTasksCounter
import com.atlassian.plugin.web.Condition
import com.atlassian.stash.pull.PullRequest


class HasOpenTasksCondition(openTasksCounter: OpenTasksCounter[PullRequest]) extends Condition {

  override def init(context: JMap[String, String]): Unit = {}

  override def shouldDisplay(context: JMap[String, AnyRef]): Boolean = context.get("pullRequest") match {
    case pullRequest: PullRequest => openTasksCounter.countOpenTasks(pullRequest) > 0
    case _ => false
  }

}

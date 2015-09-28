package ch.mibex.crossproduct.api


trait OpenTasksCounter[T] {
  def countOpenTasks(pullRequest: T): Long
}

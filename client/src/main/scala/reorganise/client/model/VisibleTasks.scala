package reorganise.client.model

import reorganise.shared.model.Task

case class VisibleTasks (tasks: Vector[Task]) {
  def updated (newTask: Task) =
    tasks.indexWhere (_.id == newTask.id) match {
      case -1    => VisibleTasks (tasks :+ newTask)                   // add new task
      case index => VisibleTasks (tasks.updated (index, newTask))     // replace old task
    }

  def remove (task: Task) =
    VisibleTasks (tasks.filterNot(_ == task))
}

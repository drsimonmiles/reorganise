package reorganise.shared.model

case class VisibleTasks (tasks: Vector[Task], lists: Vector[String]) {
  def updated (newTask: Task) =
    tasks.indexWhere (_.id == newTask.id) match {
      case -1    => copy (tasks = tasks :+ newTask)                   // add new task
      case index => copy (tasks = tasks.updated (index, newTask))     // replace old task
    }

  def remove (task: Task) =
    copy (tasks = tasks.filterNot (_ == task))
}

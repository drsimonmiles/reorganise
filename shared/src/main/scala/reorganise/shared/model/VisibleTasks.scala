package reorganise.shared.model

case class VisibleTasks (tasks: Vector[Task], lists: Vector[TaskList]) {
  def lookupList (id: Long): Option[TaskList] =
    lists.find (_.id == id)

  def updatedTask (newTask: Task) =
    tasks.indexWhere (_.id == newTask.id) match {
      case -1    => copy (tasks = tasks :+ newTask)                   // add new task
      case index => copy (tasks = tasks.updated (index, newTask))     // replace old task
    }

  def updatedList (newList: TaskList) =
    tasks.indexWhere (_.id == newList.id) match {
      case -1    => copy (lists = lists :+ newList)
      case index => copy (lists = lists.updated (index, newList))
    }

  def removeTask (task: Task) =
    copy (tasks = tasks.filterNot (_ == task))

  def removeList (listID: Long) =
    copy (lists = lists.filterNot (_.id == listID))
}

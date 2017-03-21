package reorganise.client.model

import reorganise.client.model.ModelOps._
import reorganise.shared.model.{Task, TaskList, VisibleTasks}

case class ClientState (tasks: Vector[ClientTask], lists: Vector[TaskList],
                        view: Option[ClientTasksView], taskFeature: TaskFeature, listFeature: ListFeature) {
  def this (data: VisibleTasks) =
    this (toClientTasks (data.tasks, data.lists), data.lists, None, OrderFeature, OrderFeature)

  def task (id: Long): Option[ClientTask] =
    tasks.find (_.id == id)

  def listOrder: Vector[Long] =
    lists.map (_.id)

  val viewedList: Option[TaskList] =
    view.map (_.list)

  def updatedTask (newTask: ClientTask): ClientState =
    tasks.indexWhere (_.id == newTask.id) match {
      case -1    => copy (tasks = tasks :+ newTask)                   // add new task
      case index => copy (tasks = tasks.updated (index, newTask))     // replace old task
    }

  def updatedList (newList: TaskList): ClientState =
    lists.indexWhere (_.id == newList.id) match {
      case -1    =>
        copy (lists = lists :+ newList)                   // add new list
      case index =>                                                   // replace old list
        val newTasks = tasks.map (t => if (t.list.id == newList.id) t.copy (list = newList) else t)
        val newView = view.map (tv => if (tv.list.id == newList.id) tv.copy (list = newList) else tv)
        val newState = copy (tasks = newTasks, view = newView, lists = lists.updated (index, newList))
        newState
    }

  def removeTask (task: ClientTask): ClientState =
    copy (tasks = tasks.filterNot (_ == task))

  def removeList (listID: Long): ClientState =
    copy (lists = lists.filterNot (_.id == listID))

  def withListOrder (order: Vector[Long]): ClientState =
    copy (lists = order.flatMap (id => lists.find (_.id == id)))

  def withTasks (newTasks: Vector[Task]): ClientState =
    copy (tasks = toClientTasks (newTasks, lists))

  def withVisible (data: VisibleTasks): ClientState = {
    val newView = view.flatMap (tv => data.lists.find (_.id == tv.list.id).map (l => tv.copy (list = l)))
    ClientState (toClientTasks (data.tasks, data.lists), data.lists, newView, taskFeature, listFeature)
  }
}

package reorganise.client.model

import reorganise.shared.model.{Task, TaskList, TasksView, VisibleTasks}

case class ClientState (visible: VisibleTasks, view: Option[TasksView], taskFeature: TaskFeature, listFeature: ListFeature) {
  def this (data: VisibleTasks) =
    this (data, None, OrderFeature, OrderFeature)

  def updatedList (newList: TaskList) =
    copy (visible = visible.updatedList (newList))

  def updatedTask (newTask: Task) =
    copy (visible = visible.updatedTask (newTask))

  def removeList (oldList: Long) =
    copy (visible = visible.removeList (oldList))

  def removeTask (oldTask: Task) =
    copy (visible = visible.removeTask (oldTask))

  def withListOrder (order: Vector[Long]) =
    copy (visible = visible.withListOrder (order))

  def withVisible (newVisible: VisibleTasks) =
    copy (visible = newVisible)

  def viewedList: Option[TaskList] =
    view.flatMap (current => visible.list (current.list))
}

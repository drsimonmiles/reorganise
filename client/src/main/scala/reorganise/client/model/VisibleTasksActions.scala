package reorganise.client.model

import diode.Action
import reorganise.shared.model.{Task, TaskList, TasksView, VisibleTasks}

trait VisibleTasksAction extends Action

case object LoadAllVisibleDataFromServer extends VisibleTasksAction
case class RefreshClientData (data: VisibleTasks) extends VisibleTasksAction
case class RefreshTasksForView (newTasks: Vector[Task]) extends VisibleTasksAction
case object CreateTask extends VisibleTasksAction
case class UpdateTask (task: Task) extends VisibleTasksAction
case class DeleteTask (task: Task) extends VisibleTasksAction
case class DeleteList (listID: Long) extends VisibleTasksAction
case class CreateList (isDerived: Boolean) extends VisibleTasksAction
case class UpdateList (list: TaskList) extends VisibleTasksAction
case class UpdateListOrder (order: Vector[Long]) extends VisibleTasksAction
case class ChangeView (newView: Option[TasksView]) extends VisibleTasksAction
case class ChangeTaskFeature (newFeature: TaskFeature) extends VisibleTasksAction
case class ChangeListFeature (newFeature: ListFeature) extends VisibleTasksAction
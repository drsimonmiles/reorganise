package reorganise.client.model

import diode.Action
import reorganise.shared.model.Task

trait VisibleTasksAction extends Action

case object ReloadVisibleTasksFromServer extends VisibleTasksAction
case class RefreshClientShownTasks (tasks: Vector[Task]) extends VisibleTasksAction
case class UpdateTask (task: Task) extends VisibleTasksAction
case class DeleteTask (task: Task) extends VisibleTasksAction

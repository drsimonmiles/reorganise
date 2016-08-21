package reorganise.client.comms

import autowire._
import boopickle.Default._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.{VisibleTasks, TasksView, Task}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ServerCaller {
  def createTask (view: TasksView): Future[VisibleTasks] =
    AjaxClient[TasksAPI].createTask (view).call ()

  def loadTasksFromServer (view: TasksView): Future[VisibleTasks] =
    AjaxClient[TasksAPI].loadTasks (view).call ()

  def updateTaskOnServer (task: Task, view: TasksView): Future[VisibleTasks] =
    AjaxClient[TasksAPI].updateTask (task, view).call ()

  def deleteTaskFromServer (taskID: Long, view: TasksView): Future[VisibleTasks] =
    AjaxClient[TasksAPI].deleteTask (taskID, view).call ()
}

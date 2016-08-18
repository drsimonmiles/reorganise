package reorganise.client.comms

import autowire._
import boopickle.Default._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.{TasksView, Task}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ServerCaller {
  def loadTasksFromServer (view: TasksView): Future[Vector[Task]] =
    AjaxClient[TasksAPI].loadTasks (view).call ()

  def updateTaskOnServer (task: Task, view: TasksView): Future[Vector[Task]] =
    AjaxClient[TasksAPI].updateTask (task, view).call ()

  def deleteTaskFromServer (taskID: Long, view: TasksView): Future[Vector[Task]] =
    AjaxClient[TasksAPI].deleteTask (taskID, view).call ()
}

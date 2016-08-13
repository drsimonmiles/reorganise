package reorganise.client.comms

import autowire._
import boopickle.Default._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.Task
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ServerCaller {
  def loadTasksFromServer (): Future[Vector[Task]] =
    AjaxClient[TasksAPI].loadTasks ().call ()

  def updateTaskOnServer (task: Task): Future[Vector[Task]] =
    AjaxClient[TasksAPI].updateTask (task).call ()

  def deleteTaskFromServer (taskID: Long): Future[Vector[Task]] =
    AjaxClient[TasksAPI].deleteTask (taskID).call ()
}

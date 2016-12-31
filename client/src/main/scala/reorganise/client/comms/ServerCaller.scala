package reorganise.client.comms

import autowire._
import boopickle.Default._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.{Task, TaskList, TasksView, VisibleTasks}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ServerCaller {
  def createTask: Future[VisibleTasks] =
    AjaxClient[TasksAPI].createTask ().call ()

  def createList (isDerived: Boolean): Future[VisibleTasks] =
    AjaxClient[TasksAPI].createList (isDerived).call ()

  def loadVisibleDataFromServer: Future[VisibleTasks] =
    AjaxClient [TasksAPI].loadTasks ().call ()

  def updateTaskOnServer (task: Task): Future[VisibleTasks] =
    AjaxClient[TasksAPI].updateTask (task).call ()

  def updateListOnServer (list: TaskList): Future[VisibleTasks] =
    AjaxClient[TasksAPI].updateList (list).call ()

  def updateListOrderOnServer (order: Vector[Long]): Future[VisibleTasks] =
    AjaxClient[TasksAPI].updateListOrder (order).call ()

  def deleteTaskFromServer (taskID: Long): Future[VisibleTasks] =
    AjaxClient[TasksAPI].deleteTask (taskID).call ()

  def deleteListFromServer (listID: Long): Future[VisibleTasks] =
    AjaxClient[TasksAPI].deleteList (listID).call ()

  def setView (view: Option[TasksView]): Future[Vector[Task]] =
    AjaxClient[TasksAPI].setView (view).call ()
}

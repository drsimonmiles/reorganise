package reorganise.client.comms

import autowire._
import boopickle.Default._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.{TaskList, VisibleTasks, TasksView, Task}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ServerCaller {
  def createTask: Future[VisibleTasks] =
    AjaxClient[TasksAPI].createTask ().call ()

  def createList (isDerived: Boolean): Future[VisibleTasks] =
    AjaxClient[TasksAPI].createList (isDerived).call ()

  def loadTasksFromServer: Future[VisibleTasks] =
    AjaxClient [TasksAPI].loadTasks ().call ()

  def updateTaskOnServer (task: Task): Future[VisibleTasks] =
    AjaxClient[TasksAPI].updateTask (task).call ()

  def updateListOnServer (list: TaskList): Future[VisibleTasks] =
    AjaxClient[TasksAPI].updateList (list).call ()

  def deleteTaskFromServer (taskID: Long): Future[VisibleTasks] =
    AjaxClient[TasksAPI].deleteTask (taskID).call ()

  def deleteListFromServer (listID: Long): Future[VisibleTasks] =
    AjaxClient[TasksAPI].deleteList (listID).call ()

  def setView (view: TasksView): Future[VisibleTasks] =
    AjaxClient[TasksAPI].setView (view).call ()
}

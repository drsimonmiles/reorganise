package reorganise.server

import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.Task
import scala.util.Random

class TasksService extends TasksAPI {
  var tasks = Vector (
/*    Task (id = 0, text = "First task", startDate = "2016-08-12", list = "Test", recur = Some (7), completed = false),
    Task (id = 1, text = "Second task", startDate = "2016-10-12", list = "Test", recur = None, completed = false),
    Task (id = 2, text = "Third task", startDate = "2016-06-12", list = "Other", recur = None, completed = false),
    Task (id = 3, text = "Fourth task", startDate = "2016-04-12", list = "Other", recur = None, completed = true)*/
    Task (id = 0, text = "First task", startDate = "2016-08-12", list = "Test", recur = 7, completed = false),
    Task (id = 1, text = "Second task", startDate = "2016-10-12", list = "Test", recur = -1, completed = false),
    Task (id = 2, text = "Third task", startDate = "2016-06-12", list = "Other", recur = -1, completed = false),
    Task (id = 3, text = "Fourth task", startDate = "2016-04-12", list = "Other", recur = -1, completed = true)
  )

  def loadTasks (): Vector[Task] =
    tasks

  def updateTask (task: Task): Vector[Task] = {
    if (tasks.exists (_.id == task.id))
      tasks = tasks.collect {
        case i if i.id == task.id => task
        case i => i
      }
    else
      tasks :+= task.copy (id = newTaskID)
    tasks
  }

  def deleteTask (taskID: Long): Vector[Task] = {
    tasks = tasks.filterNot (_.id == taskID)
    tasks
  }

  private def newTaskID: Long =
    Random.nextLong
}

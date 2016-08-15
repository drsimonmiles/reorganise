package reorganise.server

import java.io.{FileWriter, PrintWriter, File}
import play.api.libs.json.{JsError, JsSuccess, Json, JsValue}
import reorganise.server.model.TasksData
import reorganise.server.model.Readers._
import reorganise.server.model.Writers._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.Task
import scala.io.Source

class TasksService (tasksFile: String) extends TasksAPI {
  val currentTasksSchema = "01"

  var testTasks = Vector (
    Task (id = 0, text = "First task", startDate = "2016-08-12", list = "Test", recur = Some (7), completed = false),
    Task (id = 1, text = "Second task", startDate = "2016-10-12", list = "Test", recur = None, completed = false),
    Task (id = 2, text = "Third task", startDate = "2016-06-12", list = "Other", recur = None, completed = false),
    Task (id = 3, text = "Fourth task", startDate = "2016-04-12", list = "Other", recur = None, completed = true)
/*    Task (id = 0, text = "First task", startDate = "2016-08-12", list = "Test", recur = 7, completed = false),
    Task (id = 1, text = "Second task", startDate = "2016-10-12", list = "Test", recur = -1, completed = false),
    Task (id = 2, text = "Third task", startDate = "2016-06-12", list = "Other", recur = -1, completed = false),
    Task (id = 3, text = "Fourth task", startDate = "2016-04-12", list = "Other", recur = -1, completed = true)*/
  )

  var cachedTasks: Option[TasksData] = None

  private def retrieveTasksData: Option[TasksData] = {
    if (cachedTasks.isEmpty)
      cachedTasks = {
        val file = new File (tasksFile)
        if (file.exists) {
          val source = Source.fromFile (file).getLines.mkString
          Json.parse (source).validate [TasksData] match {
            case s: JsSuccess[TasksData] => Some (s.get)
            case e: JsError => println ("Unable to load tasks: " + e.errors); None
          }
        } else Some (TasksData (currentTasksSchema, Vector[Task] (), 0l))
      }
    cachedTasks
  }

  private def storeTasksData (): Unit =
    retrieveTasksData match {
      case Some (data) =>
        val out = new PrintWriter (new FileWriter (tasksFile))
        val json = Json.toJson (data)
        out.println (json)
        out.close ()
      case None =>
    }

  def loadTasks (): Vector[Task] =
    retrieveTasksData match {
      case Some (data) => data.tasks
      case None => Vector[Task] ()
    }

  def updateTask (task: Task): Vector[Task] = {
    retrieveTasksData match {
      case Some (data) =>
        val newTaskData =
          if (data.tasks.exists (_.id == task.id))
            data.copy (tasks = data.tasks.collect {
              case i if i.id == task.id => task
              case i => i
            })
        else
          data.copy (tasks = data.tasks :+ task.copy (id = data.nextID), nextID = data.nextID + 1)
        cachedTasks = Some (newTaskData)
        storeTasksData ()
        newTaskData.tasks
      case None => Vector[Task] ()
    }
  }

  def deleteTask (taskID: Long): Vector[Task] =
    retrieveTasksData match {
      case Some (data) =>
        cachedTasks = Some (data.copy (tasks = data.tasks.filterNot (_.id == taskID)))
        storeTasksData ()
        cachedTasks.get.tasks
      case None => Vector [Task]()
    }
}

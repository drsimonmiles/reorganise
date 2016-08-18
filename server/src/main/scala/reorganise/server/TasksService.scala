package reorganise.server

import java.io.{FileWriter, PrintWriter, File}
import java.time.LocalDate
import play.api.libs.json.{JsError, JsSuccess, Json}
import reorganise.server.model.TasksData
import reorganise.server.model.Readers._
import reorganise.server.model.Writers._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.{ListTasks, WeeksTasks, TodaysTasks, TasksView, AllTasks, Task}
import scala.io.Source

class TasksService (tasksFile: String) extends TasksAPI {
  val currentTasksSchema = "01"

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

  private def startLocalDate (task: Task) =
    LocalDate.parse (task.startDate)

  private def upToDate (tasks: Vector[Task], toDate: LocalDate): Vector[Task] =
    tasks.filter (task => toDate.isAfter (startLocalDate (task)) || toDate.isEqual (startLocalDate (task)))

  def loadTasks (view: TasksView): Vector[Task] = {
    val allTasks = retrieveTasksData match {
      case Some (data) => data.tasks
      case None => Vector[Task] ()
    }
    (view.list match {
      case AllTasks => allTasks
      case TodaysTasks => upToDate (allTasks, LocalDate.now)
      case WeeksTasks => upToDate (allTasks, LocalDate.now.plusDays (6))
      case ListTasks (list) => allTasks.filter (_.list == list)
    }).filter (view.includeCompleted || !_.completed)
  }

  def updateTask (task: Task, view: TasksView): Vector[Task] = {
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
        loadTasks (view)
      case None => Vector[Task] ()
    }
  }

  def deleteTask (taskID: Long, view: TasksView): Vector[Task] =
    retrieveTasksData match {
      case Some (data) =>
        cachedTasks = Some (data.copy (tasks = data.tasks.filterNot (_.id == taskID)))
        storeTasksData ()
        loadTasks (view)
      case None => Vector[Task] ()
    }
}

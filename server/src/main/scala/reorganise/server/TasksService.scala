package reorganise.server

import java.io.{FileWriter, PrintWriter, File}
import java.time.LocalDate
import play.api.libs.json.{JsError, JsSuccess, Json}
import reorganise.server.model.TasksData
import reorganise.server.model.Readers._
import reorganise.server.model.Writers._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.{VisibleTasks, ListTasks, WeeksTasks, TodaysTasks, TasksView, AllTasks, Task}
import scala.io.Source

class TasksService (tasksFile: String) extends TasksAPI {
  val currentTasksSchema = "01"
  val emptySharedTasksData = VisibleTasks (Vector[Task] (), Vector[String] ())
  val defaultList = "Miscellaneous"

  case class CachedData (tasksData: TasksData, lists: Vector[String]) {
    def this (tasksData: TasksData) =
      this (tasksData, tasksData.tasks.map (_.list).distinct.sorted)
  }

  var cache: Option[CachedData] = None

  private def retrieveTasksData: Option[CachedData] = {
    if (cache.isEmpty)
      cache = {
        val file = new File (tasksFile)
        if (file.exists) {
          val source = Source.fromFile (file).getLines.mkString
          Json.parse (source).validate [TasksData] match {
            case s: JsSuccess[TasksData] => Some (new CachedData (s.get))
            case e: JsError => println ("Unable to load tasks: " + e.errors); None
          }
        }
        else
          Some (CachedData (TasksData (currentTasksSchema, Vector[Task] (), 0l), Vector[String] ()))
      }
    cache
  }

  private def storeTasksData (): Unit =
    retrieveTasksData match {
      case Some (data) =>
        val out = new PrintWriter (new FileWriter (tasksFile))
        val json = Json.toJson (data.tasksData)
        out.println (json)
        out.close ()
      case None =>
    }

  private def startLocalDate (task: Task) =
    LocalDate.parse (task.startDate)

  private def upToDate (tasks: Vector[Task], toDate: LocalDate): Vector[Task] =
    tasks.filter (task => toDate.isAfter (startLocalDate (task)) || toDate.isEqual (startLocalDate (task)))

  def createTask (view: TasksView): VisibleTasks = {
    retrieveTasksData match {
      case Some (data) =>
        val list = view.list match {
          case ListTasks (viewedList) => viewedList
          case _ => defaultList
        }
        val task = Task (data.tasksData.nextID, "", LocalDate.now.toString, list, None, completed = false)
        val newTasksData =
          data.tasksData.copy (tasks = data.tasksData.tasks :+ task, nextID = data.tasksData.nextID + 1)
        cache = Some (new CachedData (newTasksData))
        storeTasksData ()
        loadTasks (view)
      case None =>
        emptySharedTasksData
    }
  }

  def loadTasks (view: TasksView): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        val visibleTasks = (view.list match {
          case AllTasks => data.tasksData.tasks
          case TodaysTasks => upToDate (data.tasksData.tasks, LocalDate.now)
          case WeeksTasks => upToDate (data.tasksData.tasks, LocalDate.now.plusDays (6))
          case ListTasks (list) => data.tasksData.tasks.filter (_.list == list)
        }).filter (view.includeCompleted || !_.completed)
        VisibleTasks (visibleTasks, data.lists)
      case None => emptySharedTasksData
    }

  def updateTask (task: Task, view: TasksView): VisibleTasks = {
    retrieveTasksData match {
      case Some (data) =>
        val newTaskData =
          if (data.tasksData.tasks.exists (_.id == task.id))
            data.tasksData.copy (tasks = data.tasksData.tasks.collect {
              case i if i.id == task.id => task
              case i => i
            })
          else
            data.tasksData//.copy (tasks = data.tasksData.tasks :+ task.copy (id = data.nextID), nextID = data.tasksData.nextID + 1)
        cache = Some (new CachedData (newTaskData))
        storeTasksData ()
        loadTasks (view)
      case None => emptySharedTasksData
    }
  }

  def deleteTask (taskID: Long, view: TasksView): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        cache = Some (new CachedData (data.tasksData.copy (tasks = data.tasksData.tasks.filterNot (_.id == taskID))))
        storeTasksData ()
        loadTasks (view)
      case None => emptySharedTasksData
    }
}

package reorganise.server

import java.io.{FileWriter, PrintWriter, File}
import java.time.LocalDate
import play.api.libs.json.{JsError, JsSuccess, Json}
import reorganise.server.model.TasksData
import reorganise.server.model.Readers._
import reorganise.server.model.Writers._
import reorganise.shared.comms.TasksAPI
import reorganise.shared.model.{TaskList, VisibleTasks, ListTasks, WeeksTasks, TodaysTasks, TasksView, AllTasks, Task}
import scala.io.Source

class TasksService (tasksFile: String) extends TasksAPI {
  val emptySharedTasksData = VisibleTasks (Vector[Task] (), Vector[TaskList] ())
  val emptyDatabase = TasksData (currentTasksSchema, Vector[Task] (), Vector[TaskList] (), 0l, 0l)

  // Data in cache, including loaded data and derived/dependent in-memory data
  case class CachedData (tasksData: TasksData)

  var cache: Option[CachedData] = None
  var view = TasksView (includeCompleted = false, list = TodaysTasks)

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
          Some (CachedData (emptyDatabase))
      }
    cache
  }

  private def defaultList: TaskList = {
    val firstList = TaskList (0l, "Miscellaneous")
    retrieveTasksData match {
      case Some (data) => data.tasksData.lists.headOption.getOrElse (firstList)
      case None => firstList
    }
  }

  private def storeTasksData (data: TasksData): VisibleTasks =
    storeCachedData (CachedData (data))

  private def storeCachedData (data: CachedData): VisibleTasks = {
    cache = Some (data)
    val out = new PrintWriter (new FileWriter (tasksFile))
    val json = Json.toJson (data.tasksData)
    out.println (json)
    out.close ()
    loadTasks ()
  }

  private def startLocalDate (task: Task) =
    LocalDate.parse (task.startDate)

  private def upToDate (tasks: Vector[Task], toDate: LocalDate): Vector[Task] =
    tasks.filter (task => toDate.isAfter (startLocalDate (task)) || toDate.isEqual (startLocalDate (task)))

  def createTask (): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        val listID = view.list match {
          case ListTasks (viewedListID) => viewedListID
          case _ => defaultList.id
        }
        val task = Task (data.tasksData.nextTaskID, "", LocalDate.now.toString, listID, None, completed = false)
        storeTasksData (data.tasksData.copy (tasks = data.tasksData.tasks :+ task, nextTaskID = data.tasksData.nextTaskID + 1))
      case None =>
        emptySharedTasksData
    }

  def createList (): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        val nextID = data.tasksData.nextListID
        val newList = TaskList (nextID, "New list")
        storeTasksData (data.tasksData.copy (lists = data.tasksData.lists :+ newList, nextListID = nextID + 1))
      case None =>
        emptySharedTasksData
    }

  def loadTasks (): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        val visibleTasks = (view.list match {
          case AllTasks => data.tasksData.tasks
          case TodaysTasks => upToDate (data.tasksData.tasks, LocalDate.now)
          case WeeksTasks => upToDate (data.tasksData.tasks, LocalDate.now.plusDays (6))
          case ListTasks (list) => data.tasksData.tasks.filter (_.list == list)
        }).filter (view.includeCompleted || !_.completed)
        VisibleTasks (visibleTasks, data.tasksData.lists)
      case None => emptySharedTasksData
    }

  def updateTask (task: Task): VisibleTasks = {
    retrieveTasksData match {
      case Some (data) =>
        val newTaskData =
          if (data.tasksData.tasks.exists (_.id == task.id))
            data.tasksData.copy (tasks = data.tasksData.tasks.collect {
              case i if i.id == task.id => task
              case i => i
            })
          else
            data.tasksData
        storeTasksData (newTaskData)
      case None => emptySharedTasksData
    }
  }

  def updateList (list: TaskList): VisibleTasks = {
    retrieveTasksData match {
      case Some (data) =>
        val newTaskData =
          if (data.tasksData.lists.exists (_.id == list.id))
            data.tasksData.copy (lists = data.tasksData.lists.collect {
              case i if i.id == list.id => list
              case i => i
            })
          else
            data.tasksData
        storeTasksData (newTaskData)
      case None => emptySharedTasksData
    }
  }

  def deleteTask (taskID: Long): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        storeTasksData (data.tasksData.copy (tasks = data.tasksData.tasks.filterNot (_.id == taskID)))
      case None => emptySharedTasksData
    }

  def deleteList (listID: Long): VisibleTasks =
    retrieveTasksData match {
      case Some (data) =>
        storeTasksData (data.tasksData.copy (lists = data.tasksData.lists.filterNot (_.id == listID)))
      case None => emptySharedTasksData
    }

  def setView (newView: TasksView): VisibleTasks = {
    view = newView
    loadTasks ()
  }
}

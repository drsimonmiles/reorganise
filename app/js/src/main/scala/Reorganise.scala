@JSExport
object Reorganise {
  @JSExport
  def main (area: html.Div): Unit = {
    val database = Vector[TaskList] (
      TaskList (name = "One", tasks = Vector[Task] ()),
      TaskList (name = "Three", tasks = Vector[Task] ())
    )

    def taskListItem (taskList: TaskList) =
      button (style := "width: 100%; background-color: #FFE0E0;")(taskList.name)
    val addTaskList =
      button (style := "width: 100%; background-color: #FFE0E0;")("+")
    val taskListContents =
      database.map (taskListItem) :+ addTaskList
    def taskPanel (task: Task) =
      div (style := "background-color: #F0F0F0; border-style: ridge;")(
        if (task.completed)
          input (style := "width: 30px; float: left;")(`type` := "checkbox", checked := "checked")
        else
          input (style := "width: 30px; float: left;")(`type` := "checkbox"),
        label (task.text),
        label (style := "float: right; background-color: #F0FFF0;")(task.due.toString)
      )

    area.appendChild (
      div (style := "box-sizing: border-box;") (
        div (style := "width: 200px; float: left; box-sizing: border-box; background-color: #000000;")(
          ul (taskListContents:_*)
        ),
        div (style := "margin-left: 200px; overflow: hidden; box-sizing: border-box;")(
          input (style := "width: 100%; background-color: #F0F0FF; border-style: solid;")(`type`:="text"),
          taskPanel (Task (completed = false, "Finish Reorganise", LocalDate.now)),
          taskPanel (Task (completed = true, "Something else", LocalDate.now))
        )
      ).render
    )
  }
}

package reorganise.client.components

import japgolly.scalajs.react.{ReactComponentB, Callback}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.styles.GlobalStyles
import reorganise.shared.model.Task
import scalacss.ScalaCssReact._

object TaskList {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class TaskListProps (tasks: Seq[Task], stateChange: Task => Callback, editItem: Task => Callback)

  private val TaskList = ReactComponentB[TaskListProps] ("TaskList")
    .render_P (p =>
      <.ul (bss.listGroup.listGroup) (p.tasks.map {
        task => TaskRow (task, p.stateChange, p.editItem)
      })).build

  def apply (items: Seq[Task], stateChange: Task => Callback, editItem: Task => Callback) =
    TaskList (TaskListProps (items, stateChange, editItem))
}

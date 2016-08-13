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
    .render_P (p => {
      def renderItem (task: Task) = {
        <.li (bss.listGroup.item,
          <.input.checkbox (^.checked := task.completed, ^.onChange --> p.stateChange (task.copy (completed = !task.completed))),
          <.span(" "),
          if (task.completed) <.s (task.text) else <.span (task.text),
          Button(Button.Props (p.editItem (task), addStyles = Seq (bss.pullRight, bss.buttonXS)), "Edit")
        )
      }
      <.ul (bss.listGroup.listGroup)(p.tasks map renderItem)
    }).build

  def apply (items: Seq[Task], stateChange: Task => Callback, editItem: Task => Callback) =
    TaskList (TaskListProps (items, stateChange, editItem))
}

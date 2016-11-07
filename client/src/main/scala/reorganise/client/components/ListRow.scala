package reorganise.client.components

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import reorganise.client.styles.GlobalStyles
import reorganise.shared.model.TasksView
import scalacss.ScalaCssReact._

object ListRow {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (list: TaskListListItem, isCurrent: Boolean, onClick: Callback)

  val component = ReactComponentB[Props] ("ListRow")
    .render_P (p =>
      <.li (bss.listGroup.itemOpt (p.list.style), p.list.label,
        ^.onClick --> p.onClick, p.isCurrent ?= (^.className := "active"))
    ).build

  def apply (item: TaskListListItem, isCurrent: Boolean, setList: Option[TasksView] => Callback) =
    component (Props (item, isCurrent, setList (item.view)))
}

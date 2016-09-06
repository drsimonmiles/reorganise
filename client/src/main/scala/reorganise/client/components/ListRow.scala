package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import reorganise.client.model.{ChangeView, LoadableModel}
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

object ListRow {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (list: TaskListListItem, isCurrent: Boolean, onClick: Callback)

  val component = ReactComponentB[Props] ("ListRow")
    .render_P (p =>
      <.li (bss.listGroup.itemOpt (p.list.style), p.list.label,
        ^.onClick --> p.onClick, p.isCurrent ?= (^.className := "active"))
    ).build

  def apply (item: TaskListListItem, model: ModelProxy[LoadableModel]) =
    component (Props (item, model.value.view.list == item.view.list,
      model.dispatch (ChangeView (model.value.view.copy (list = item.view.list)))))
}

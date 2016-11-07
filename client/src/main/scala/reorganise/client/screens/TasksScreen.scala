package reorganise.client.screens

import diode.data.Ready
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import reorganise.client.components.{ListsSidebar, ViewedItemsTable}
import reorganise.client.model.{ChangeView, LoadableModel, ModelPoint, ReloadVisibleTasksFromServer}
import reorganise.client.styles.GlobalStyles.bootstrapStyles
import scalacss.ScalaCssReact._

object TasksScreen {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB [ModelPoint[LoadableModel, LoadableModel]]("TaskScreen")
    .render_P { p =>
      p.value.data match {
        case Ready (data) =>
          <.div (bss.row,
            <.div (bss.columns (2), ListsSidebar (p.zoom (_.data.get.view).
              scopedVariable (p.zoom (_.data.get.visible.lists), ChangeView))),
            <.div (bss.columns (10), ViewedItemsTable (p.zoom (_.data.get)))
          )
        case _ => <.div ("Loading...")
      }
    }.componentDidMount { scope =>
    Callback.when (scope.props.zoom (_.data).value.isEmpty)(scope.props.dispatch (ReloadVisibleTasksFromServer))
  }.build

  def apply (proxy: ModelPoint[LoadableModel, LoadableModel]) =
    proxy.createReader (component)
}

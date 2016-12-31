package reorganise.client.screens

import diode.data.Ready
import diode.react.ModelProxy
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import reorganise.client.components.{ListsSidebar, ViewedItemsTable}
import directed.VariableOps._
import reorganise.client.model.{ChangeView, LoadAllVisibleDataFromServer, LoadableModel}
import reorganise.client.styles.GlobalStyles.bootstrapStyles
import scalacss.ScalaCssReact._

object TasksScreen {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB[ModelProxy[LoadableModel]] ("TaskScreen")
    .render_P { p =>
      p.value.data match {
        case Ready (data) =>
          <.div (bss.row,
            <.div (bss.columns (2), ListsSidebar (p.value.data.get.visible.lists,
              p.zoom (_.data.get.view).variable (ChangeView))),
            <.div (bss.columns (10), ViewedItemsTable (p.zoom (_.data.get)))
          )
        case _ => <.div ("Loading...")
      }
    }.componentDidMount { scope =>
    Callback.when (scope.props.zoom (_.data).value.isEmpty)(scope.props.dispatchCB (LoadAllVisibleDataFromServer))
  }.build
}

package reorganise.client.screens

import diode.data.{Pot, Ready}
import diode.react.ModelProxy
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import reorganise.client.components.{ListsSidebar, ViewedItemsTable}
import reorganise.client.model.{ChangeView, ClientState, LoadAllVisibleDataFromServer}
import reorganise.client.model.ModelVariables._
import reorganise.client.styles.GlobalStyles.bootstrapStyles
import scalacss.ScalaCssReact._

object TasksScreen {
  @inline private def bss = bootstrapStyles

  val component = ReactComponentB[ModelProxy[Pot[ClientState]]] ("TaskScreen")
    .render_P { p =>
      p.value match {
        case Ready (data) =>
          <.div (bss.row,
            <.div (bss.columns (2), ListsSidebar (p.value.get.lists, ChangeView.from (data.view))),
            <.div (bss.columns (10), ViewedItemsTable (data))
          )
        case _ => <.div ("Loading...")
      }
    }.componentDidMount { scope =>
    Callback.when (scope.props.value.isEmpty)(scope.props.dispatchCB (LoadAllVisibleDataFromServer))
  }.build
}

package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react.{Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.{Icon, Button, Dropdown}
import reorganise.client.model.{OrderFeature, ChangeFeature, ListFeature, RecurFeature, StartFeature, TaskFeature, LoadableModel}
import reorganise.client.styles.BootstrapAlertStyles.warning
import reorganise.client.styles.GlobalStyles._
import scalacss.ScalaCssReact._

object TaskStatusBar {
  @inline private def bss = bootstrapStyles

  def featureDropdown (p: ModelProxy[LoadableModel]) = {
    val features = Vector (ListFeature, StartFeature, RecurFeature, OrderFeature)
    def setFeature (feature: TaskFeature) = p.dispatch (ChangeFeature (feature))
    Dropdown[TaskFeature] ("feature", p.value.feature.label, warning, features, _.label, setFeature)
  }

  val component = ReactComponentB[ModelProxy[LoadableModel]] ("TaskStatusBar")
    .render_P { p =>
      <.div (bss.row, compact,
        <.div (bss.columns (10),
          <.div (bss.inputAppend,
            <.span (""),
            Button (Button.Props (onClick = Callback.empty, addStyles = Seq (bss.pullRight)), Icon.cog, " List settings")
        )),
        <.div (bss.columns (2), featureDropdown (p))
      )
    }.build

  def apply (proxy: ModelProxy[LoadableModel]) = component (proxy)
}

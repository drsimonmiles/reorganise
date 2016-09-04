package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.Dropdown
import reorganise.client.model.{ChangeFeature, ListFeature, RecurFeature, StartFeature, TaskFeature, LoadableModel}
import reorganise.client.styles.BootstrapAlertStyles.warning
import reorganise.client.styles.GlobalStyles._
import scalacss.ScalaCssReact._

object TaskStatusBar {
  @inline private def bss = bootstrapStyles

  def featureDropdown (p: ModelProxy[LoadableModel]) = {
    val features = Vector (ListFeature, StartFeature, RecurFeature)
    def setFeature (feature: TaskFeature) = p.dispatch (ChangeFeature (feature))
    Dropdown[TaskFeature] (p.value.feature.label, warning, features, _.label, setFeature)
  }

  val component = ReactComponentB[ModelProxy[LoadableModel]] ("TaskStatusBar")
    .render_P { p =>
      <.div (bss.row, compact,
        <.div (bss.columns (10), <.span ("")),
        <.div (bss.columns (2), featureDropdown (p))
      )
    }.build

  def apply (proxy: ModelProxy[LoadableModel]) = component (proxy)
}

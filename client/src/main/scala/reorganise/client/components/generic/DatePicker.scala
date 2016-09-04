package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.querki.facades.bootstrap.datepicker._

object DatePicker {
  val baseOpts = BootstrapDatepickerOptions.
    autoclose (true).
    todayHighlight (true).
    todayBtnLinked ().
    disableTouchKeyboard (true).
    orientation (Orientation.Top)

  class Backend (t: BackendScope[String, Unit]) {
    def render (p: String) =
      <.input (^.tpe := "text", ^.value := p)
  }

  val component = ReactComponentB[String] ("DatePicker")
    .renderBackend[Backend]
    .componentDidMount (scope => Callback {
      $ (scope.getDOMNode ()).datepicker (baseOpts)
    })
    .build

  def apply (date: String) =
    component (date)
}

package reorganise.client.components

import directed.DiodeVariable
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.facades.bootstrap.datepicker._
import org.querki.jquery._

class DatePicker {
  val baseOpts = BootstrapDatepickerOptions.
    autoclose (true).
    todayHighlight (true).
    todayBtnLinked ().
    disableTouchKeyboard (true).
    orientation (Orientation.Top)._result

  class Backend (t: BackendScope[DiodeVariable[String], Unit]) {
    def render (p: DiodeVariable[String]) =
      <.input (^.tpe := "text", ^.value := p.value,
        ^.onChange ==> { event: ReactEventI => p.set (event.target.value)}
      )
  }

  val component = ReactComponentB[DiodeVariable[String]] ("DatePicker")
    .renderBackend[Backend]
    .componentDidMount (scope => Callback {
      $ (scope.getDOMNode ()).datepicker (baseOpts)
    })
    .build

  def apply (data: DiodeVariable[String]) =
    component (data)
}

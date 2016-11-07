package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.facades.bootstrap.datepicker._
import org.querki.jquery._
import reorganise.client.model.generic.Variable

class DatePicker[Model <: AnyRef] {
  val baseOpts = BootstrapDatepickerOptions.
    autoclose (true).
    todayHighlight (true).
    todayBtnLinked ().
    disableTouchKeyboard (true).
    orientation (Orientation.Top)

  class Backend (t: BackendScope[Variable[Model, String], Unit]) {
    def render (p: Variable[Model, String]) =
      <.input (^.tpe := "text", ^.value := p.value,
        ^.onChange ==> { event: ReactEventI => p.set (event.target.value)}
      )
  }

  val component = ReactComponentB[Variable[Model, String]] ("DatePicker")
    .renderBackend[Backend]
    .componentDidMount (scope => Callback {
      $ (scope.getDOMNode ()).datepicker (baseOpts)
    })
    .build

  def apply (data: Variable[Model, String]) =
    data.createEditor (component)
}

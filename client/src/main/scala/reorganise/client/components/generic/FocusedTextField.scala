package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.generic.Variable
import reorganise.client.styles.GlobalStyles._
import scalacss.ScalaCssReact._

class FocusedTextField (placeholder: String) {
  @inline private def bss = bootstrapStyles

  class Backend (scope: BackendScope[Variable[_, String], String]) {
    def render (props: Variable[_, String], state: String): ReactElement =
      <.input.text (bss.formControl, ^.id := "description", ^.value := state,
        ^.placeholder := placeholder,
        ^.onChange ==> { event: ReactEventI => scope.setState (event.target.value) },
        ^.onKeyPress ==> { event: ReactKeyboardEventI =>
          Callback (if (event.charCode == 13) event.currentTarget.blur ())
        },
        ^.onBlur ==> { e: ReactEventI => props.set (state) }
      )
  }

  val component = ReactComponentB[Variable[_, String]]("FocusedTextField")
    .initialState_P (_.value)
    .renderBackend[Backend]
    .build

  def apply (variable: Variable[_, String]) =
    //variable.createEditor (component)
    component (variable)
}

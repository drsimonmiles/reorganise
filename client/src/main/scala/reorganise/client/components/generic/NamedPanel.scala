package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.styles.BootstrapAlertStyles.default
import reorganise.client.styles.{BootstrapAlertStyles, GlobalStyles}
import scalacss.ScalaCssReact._

object NamedPanel {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (name: String, nameChange: String => Callback, editableName: Boolean,
                    style: BootstrapAlertStyles.Value = default)

  class Backend (t: BackendScope[Props, String]) {
    def render (p: Props, state: String, children: PropsChildren): ReactElement = {
      <.div (bss.panelOpt (p.style),
        <.div (bss.panelHeading,
          <.input.text (bss.formControl, ^.id := "name", ^.value := state,
            ^.placeholder := "write list name",
            !p.editableName ?= (^.disabled := "true"),
            ^.onChange ==> { e: ReactEventI => t.setState (e.target.value) },
            ^.onKeyPress ==> { e: ReactKeyboardEventI =>
              Callback (if (e.charCode == 13) e.currentTarget.blur ())
            },
            ^.onBlur ==> { e: ReactEventI => p.nameChange (state) }
          )
        ),
        <.div (bss.panelBody, children)
      )
    }
  }

  def component = ReactComponentB[Props]("NamedPanel")
    .initialState_P (p => p.name)
    .renderBackend[Backend]
    .build

  def apply (props: Props, children: ReactNode*) = component (props, children: _*)
  def apply () = component
}

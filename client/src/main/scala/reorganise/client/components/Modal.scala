package reorganise.client.components

import japgolly.scalajs.react.{ReactElement, ReactComponentB, PropsChildren, BackendScope, ReactNode, Callback}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.styles.GlobalStyles
import scala.scalajs.js
import scalacss.ScalaCssReact._

object Modal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  // header and footer are functions, so that they can get access to the the hide() function for their buttons
  case class Props (header: Callback => ReactNode, footer: Callback => ReactNode, closed: Callback,
                    backdrop: Boolean = true, keyboard: Boolean = true)

  class Backend (t: BackendScope[Props, Unit]) {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery (t.getDOMNode ()).modal ("hide")
    }

    // jQuery event handler to be fired when the modal has been hidden
    def hidden(e: JQueryEventObject): js.Any = {
      // inform the owner of the component that the modal was closed/hidden
      t.props.flatMap (_.closed).runNow ()
    }

    def render(p: Props, c: PropsChildren) = {
      val modalStyle = bss.modal
      <.div (modalStyle.modal, modalStyle.fade, ^.role := "dialog", ^.aria.hidden := true,
        <.div (modalStyle.dialog,
          <.div (modalStyle.content,
            <.div (modalStyle.header, p.header(hide)),
            <.div (modalStyle.body, c),
            <.div (modalStyle.footer, p.footer(hide))
          )
        )
      )
    }
  }

  val component = ReactComponentB[Props] ("Modal")
    .renderBackend[Backend]
    .componentDidMount (scope => Callback {
      val p = scope.props
      // instruct Bootstrap to show the modal
      jQuery (scope.getDOMNode()).modal (js.Dynamic.literal ("backdrop" -> p.backdrop, "keyboard" -> p.keyboard, "show" -> true))
      // register event listener to be notified when the modal is closed
      jQuery (scope.getDOMNode()).on ("hidden.bs.modal", null, null, scope.backend.hidden _)
    })
    .build

  def apply (props: Props, children: ReactElement*) = component(props, children: _*)
  def apply () = component
}


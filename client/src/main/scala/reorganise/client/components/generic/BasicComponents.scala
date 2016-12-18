package reorganise.client.components.generic

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactEventI, ReactKeyboardEventI}
import reorganise.client.components.generic.ComponentPatterns._
import reorganise.client.styles.{BootstrapAlertStyles, GlobalStyles}
import scalacss.ScalaCssReact._

//noinspection TypeAnnotation
object BasicComponents {
  private def bss = GlobalStyles.bootstrapStyles

  val checkbox = editor [Boolean]("Checkbox", v =>
    <.input.checkbox (^.checked := v.value, ^.onChange --> v.set (!v.value))
  )

  def dropdown[Item] (fieldID: String, label: Item => String, style: BootstrapAlertStyles.Value, items: Seq[Item]) =
    editor [Item]("Dropdown", v =>
      <.div (bss.dropdown, ^.id := fieldID,
        <.button (bss.buttonOpt (style), bss.dropdownButton, ^.tpe := "button", bss.dataToggle := "dropdown",
          label (v.value) + " ", <.span (bss.caret)),
        <.ul (bss.dropdownMenu,
          items.map (item => <.li (bss.listGroup.item, label (item), ^.onClick --> v.set (item)))
        )
      )
    )

  def focusedTextField (placeholder: String) = atomicEditor[String] ("FocusedTextField", v =>
    <.input.text (bss.formControl, ^.id := "description", ^.value := v.state,
      ^.placeholder := placeholder,
      ^.onChange ==> { event: ReactEventI => v.set (event.target.value) },
      ^.onKeyPress ==> { event: ReactKeyboardEventI =>
        Callback (if (event.charCode == 13) event.currentTarget.blur ())
      },
      ^.onBlur ==> { e: ReactEventI => v.commit }
    )
  )


}

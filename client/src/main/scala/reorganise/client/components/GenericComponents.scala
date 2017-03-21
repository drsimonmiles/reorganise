package reorganise.client.components

import directed.ComponentPatterns._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactEventI, ReactKeyboardEventI, ReactNode}
import reorganise.client.components.FAIcon.{minus, plus}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

//noinspection TypeAnnotation
object GenericComponents {
  private def bss = GlobalStyles.bootstrapStyles

  val buttonItem = containerItem[Callback] ("Button") { s =>
    <.button (bss.buttonOpt (default), ^.tpe := "button", ^.onClick --> s.props, s.children)
  }

  val button = container[Callback] ("Button") { s =>
    <.button (bss.buttonOpt (default), ^.tpe := "button", ^.onClick --> s.props, s.children)
  }

  val buttonSmallItem = containerItem[Callback] ("Button") { s =>
    <.button (bss.buttonOpt (default), bss.buttonSmall, ^.tpe := "button", ^.onClick --> s.props, s.children)
  }

  val buttonSmall = container[Callback] ("Button") { s =>
    <.button (bss.buttonOpt (default), bss.buttonSmall, ^.tpe := "button", ^.onClick --> s.props, s.children)
  }

  val checkbox = editor[Boolean] ("Checkbox") { v =>
    <.input.checkbox (^.checked := v.value, ^.onChange --> v.set (!v.value))
  }

  def dropdown[Item] (fieldID: String, label: Item => String, style: AlertStyle, items: Seq[Item]) =
    editor [Item]("Dropdown") { v =>
      <.div (bss.dropdown, ^.id := fieldID,
        <.button (bss.buttonOpt (style), bss.dropdownButton, ^.tpe := "button", bss.dataToggle := "dropdown",
          label (v.value) + " ", <.span (bss.caret)),
        <.ul (bss.dropdownMenu,
          items.map (item => <.li (bss.listGroup.item, label (item), ^.onClick --> v.set (item)))
        )
      )
    }

  def focusedTextField (placeholder: String) = atomicEditor[String] ("FocusedTextField") { v =>
    <.input.text (bss.formControl, ^.id := "description", ^.value := v.state,
      ^.placeholder := placeholder,
      ^.onChange ==> { event: ReactEventI => v.set (event.target.value) },
      ^.onKeyPress ==> { event: ReactKeyboardEventI =>
        Callback (if (event.charCode == 13) event.currentTarget.blur ())
      },
      ^.onBlur ==> { _: ReactEventI => v.commit }
    )
  }

  def panel (heading: String, children: ReactNode*) =
    <.div (bss.panel, <.div (bss.panelHeading, heading), <.div (bss.panelBody, children))

  val quantityControl = editor[Int] ("QuantityControl") { p =>
    def button (icon: ReactNode, increment: Int) =
      <.span (bss.inputGroup.button,
        <.button (^.tpe := "button", bss.buttonOpt (default), icon,
          ^.onClick --> p.set ((p.value + increment).max (0))))
    <.div (bss.inputGroup.inputGroup,
      button (minus (), -1),
      <.input (bss.formControl, ^.tpe := "text", ^.name := "quantity", ^.value := p.value, ^.disabled := "yes"),
      button (plus (), 1)
    )
  }
}

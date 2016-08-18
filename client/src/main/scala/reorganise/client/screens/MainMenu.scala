package reorganise.client.screens

import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactElement, ReactNode}
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.Icon.{check, Icon}
import reorganise.client.routes.{TasksScreenID, ScreenID}
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

object MainMenu {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (router: RouterCtl[ScreenID], currentLoc: ScreenID)

  private case class MenuItem (idx: Int, label: (Props) => ReactNode, icon: Icon, location: ScreenID)

  private val menuItems = Seq (
    MenuItem (1, _ => "Tasks", check, TasksScreenID)
  )

  private class Backend ($: BackendScope[Props, Unit]) {
    def render (props: Props) =
      <.ul (bss.navbar) (
        // build a list of menu items
        for (item <- menuItems) yield
          <.li (^.key := item.idx, (props.currentLoc == item.location) ?= (^.className := "active"),
            props.router.link (item.location)(item.icon, " ", item.label (props))
          )
      )
  }

  private val component = ReactComponentB[Props] ("MainMenu")
    .renderBackend[Backend]
    .build

  def apply (routerControl: RouterCtl[ScreenID], currentScreen: ScreenID): ReactElement =
    component (Props (routerControl, currentScreen))
}

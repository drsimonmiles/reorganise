package reorganise.client.styles

// Common Bootstrap contextual styles
object BootstrapAlertStyles {
  sealed trait AlertStyle

  case object default extends AlertStyle
  case object primary extends AlertStyle
  case object success extends AlertStyle
  case object info extends AlertStyle
  case object warning extends AlertStyle
  case object danger extends AlertStyle

  val alertStyles = Vector (default, primary, success, info, warning, danger)
}

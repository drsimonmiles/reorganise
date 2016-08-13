package reorganise.client.routes

sealed trait ScreenID
case object TasksScreenID extends ScreenID
case object ErrorScreenID extends ScreenID

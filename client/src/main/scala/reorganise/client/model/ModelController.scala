package reorganise.client.model

import diode.{Action, Circuit}
import diode.data.{Empty, Pot}
import diode.react.ReactConnector
import japgolly.scalajs.react.Callback

object ModelController extends Circuit[Pot[ClientState]] with ReactConnector[Pot[ClientState]] {
  def initialModel = Empty

  def actionHandler: ModelController.HandlerFunction =
    new ClientStateUpdater (zoomRW (x => x) ((model, value) => value))

  def dispatchCB (action: Action): Callback =
    Callback (dispatch (action))
}

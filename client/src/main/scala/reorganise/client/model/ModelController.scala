package reorganise.client.model

import diode.Circuit
import diode.data.{Empty, Pot}
import diode.react.ReactConnector

object ModelController extends Circuit[Pot[ClientState]] with ReactConnector[Pot[ClientState]] {
  def initialModel = Empty

  def actionHandler: ModelController.HandlerFunction =
    new ClientStateUpdater (zoomRW (x => x) ((model, value) => value))
}

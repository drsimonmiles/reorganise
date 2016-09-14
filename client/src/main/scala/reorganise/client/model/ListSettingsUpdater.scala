package reorganise.client.model

import diode.{ActionHandler, ModelRW}

class ListSettingsUpdater[Model] (modelRW: ModelRW[Model, Boolean]) extends ActionHandler (modelRW) {
  def handle = {
    case ChangeSettingsView (newSetting) =>
      updated (newSetting)
  }
}

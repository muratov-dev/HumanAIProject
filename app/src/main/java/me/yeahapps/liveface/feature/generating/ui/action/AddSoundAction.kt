package me.yeahapps.liveface.feature.generating.ui.action


sealed interface AddSoundAction {
    data object PlaySound : AddSoundAction
    data object PauseSound : AddSoundAction
    data object StartGenerating : AddSoundAction
    data object NavigateUp : AddSoundAction
}
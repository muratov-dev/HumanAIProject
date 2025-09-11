package me.yeahapps.liveface.feature.onboarding.ui.event

sealed interface OnboardingEvent {

    data object ShowNextSlide : OnboardingEvent
    data object ShowPreviousSlide : OnboardingEvent
}
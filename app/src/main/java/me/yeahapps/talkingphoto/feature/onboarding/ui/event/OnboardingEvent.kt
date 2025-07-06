package me.yeahapps.talkingphoto.feature.onboarding.ui.event

sealed interface OnboardingEvent {

    data object ShowNextSlide : OnboardingEvent
    data object ShowPreviousSlide : OnboardingEvent
}
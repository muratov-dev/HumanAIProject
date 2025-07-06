package me.yeahapps.talkingphoto.feature.subscription.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import me.yeahapps.talkingphoto.core.data.BillingManager
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.subscription.ui.action.SubscriptionsAction
import me.yeahapps.talkingphoto.feature.subscription.ui.event.SubscriptionsEvent
import me.yeahapps.talkingphoto.feature.subscription.ui.screen.getWeeks
import me.yeahapps.talkingphoto.feature.subscription.ui.state.SubscriptionsState
import javax.inject.Inject

@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val billingManager: BillingManager
) : BaseViewModel<SubscriptionsState, SubscriptionsEvent, SubscriptionsAction>(SubscriptionsState()) {

    override fun obtainEvent(viewEvent: SubscriptionsEvent) {
        when (viewEvent) {
            is SubscriptionsEvent.LaunchPurchaseFlow -> {
                billingManager.launchPurchaseFlow(viewEvent.activity, viewEvent.details)
            }

            is SubscriptionsEvent.SelectSubscription -> updateViewState { copy(selectedDetails = viewEvent.details) }
            SubscriptionsEvent.CloseScreen -> sendAction(SubscriptionsAction.CloseScreen)
            SubscriptionsEvent.ActivateRelativesSubscription -> {
                billingManager.activateRelativesSubscription()
                sendAction(SubscriptionsAction.RelativeSubscriptionActivated)
            }
        }
    }

    init {
        viewModelScoped {
            billingManager.availableSubscriptions.collectLatest { subscriptions ->
                subscriptions.forEach { product ->
                    if (getWeeks(product) > 1) {
                        updateViewState { copy(selectedDetails = product) }
                    }
                }
                updateViewState { copy(subscriptionsList = subscriptions) }
            }
        }
        viewModelScoped {
            billingManager.isSubscribed.collectLatest {
                if (it) sendAction(SubscriptionsAction.CloseScreen)
            }
        }
    }
}
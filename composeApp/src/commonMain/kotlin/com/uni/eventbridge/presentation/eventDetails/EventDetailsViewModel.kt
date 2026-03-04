package com.uni.eventbridge.presentation.eventDetails

import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class EventDetailsViewModel(
    private val eventRepository: EventRepository,
    val eventId: Long
) : BaseViewModel<EventDetailsUiState, EventDetailsUIEffect>(
    initialState = EventDetailsUiState()
) {

    init {
        loadEventDetails()
    }

    private fun loadEventDetails() {
        tryToExecute(
            callee = {
                eventRepository.getEventDealsById(eventId)
            },
            onStart = {
                updateState { it.copy(isLoading = true) }
            },
            onSuccess = { event ->
                updateState {
                    event.toUiState().copy(isLoading = false)
                }
            },
        )
    }

    fun onJoinEventClick() {
        tryToExecute(
            callee = {
                eventRepository.joinEvent(eventId)
            },
            onStart = {
                updateState { it.copy(isLoading = true) }
            },
            onSuccess = {
                updateState { it.copy(isLoading = false, isRegistered = true) }
                sendEffect(EventDetailsUIEffect.ShowJoinSuccessSnakeBar)
            },
        )
    }

    fun onBackClicked() {
        sendEffect(EventDetailsUIEffect.NavigateBack)
    }

}
package com.uni.eventbridge.presentation.events

import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class EventsViewModel(
    private val eventRepository: EventRepository,
) : BaseViewModel<EventsUiState, EventsUIEffect>(
    initialState = EventsUiState()
) {

    init {
        loadMyEvents()
    }

    private fun loadMyEvents() {

        tryToExecute(

            callee = {
                eventRepository.getMyEvents()
            },

            onStart = {
                updateState { it.copy(isLoading = true) }
            },
            onSuccess = { events ->
                updateState {
                    it.copy(
                        isLoading = false,
                        events = events.map { event -> event.toUiState() },
                    )
                }
            },

            )
    }

    fun onEventClicked(eventId: Long) {
        sendEffect(EventsUIEffect.NavigateToEventDetails(eventId))
    }

    fun onCreateEventClicked() {
        sendEffect(EventsUIEffect.NavigateToCreateEvent)
    }
}
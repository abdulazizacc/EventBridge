package com.uni.eventbridge.presentation.eventDetails

import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.presentation.common.BaseViewModel
import io.github.aakira.napier.Napier

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
                val event = eventRepository.getEventDetailsById(eventId)
                val joined = eventRepository.isUserJoined(eventId)
                Pair(event, joined)
            },
            onStart = {
                updateState { it.copy(isLoading = true) }
            },
            onSuccess = { (event, joined) ->
                Napier.d(tag = "loadEventDetails", message = "onSuccess called")
                updateState {
                    event.toUiState().copy(
                        isLoading = false,
                        isRegistered = joined,
                    )
                }
            },
            onError = { throwable ->
                Napier.e(
                    tag = "loadEventDetails",
                    message = "Error: ${throwable.message} cause: ${throwable.cause}"
                )
                updateState { it.copy(isLoading = false) }
                sendEffect(EventDetailsUIEffect.ShowErrorSnackBar("Failed to load event details"))
            }
        )
    }

    fun onJoinEventClick() {
        val state = currentState
        if (state.isFull) {
            sendEffect(EventDetailsUIEffect.ShowErrorSnackBar("Sorry, this event is full"))
            return
        }
        tryToExecute(
            callee = { eventRepository.joinEvent(eventId) },
            onStart = { updateState { it.copy(isLoading = true) } },
            onSuccess = {
                updateState {
                    it.copy(
                        isLoading = false,
                        isRegistered = true,
                        remainingSeats = it.remainingSeats?.minus(1),
                    )
                }
                sendEffect(EventDetailsUIEffect.ShowJoinSuccessSnakeBar)
            },
            onError = {
                updateState { it.copy(isLoading = false) }
                if (state.remainingSeats != null && state.remainingSeats <= 0) {
                    sendEffect(EventDetailsUIEffect.ShowErrorSnackBar("Sorry, this event is full"))
                }
            }
        )
    }

    fun onLeaveEventClick() {
        tryToExecute(
            callee = { eventRepository.leaveEvent(eventId) },
            onStart = { updateState { it.copy(isLoading = true) } },
            onSuccess = {
                updateState {
                    it.copy(
                        isLoading = false,
                        isRegistered = false,
                        remainingSeats = it.remainingSeats?.plus(1),
                    )
                }
                sendEffect(EventDetailsUIEffect.ShowLeaveSuccessSnackBar)
            },
            onError = {
                updateState { it.copy(isLoading = false) }
                sendEffect(EventDetailsUIEffect.ShowErrorSnackBar("Failed to leave event. Please try again."))
            }
        )
    }

    fun onNavigateClick() {
        val lat = currentState.pinLatitude ?: return
        val lon = currentState.pinLongitude ?: return
        sendEffect(EventDetailsUIEffect.OpenNavigationMap(lat, lon))
    }

    fun onBackClicked() {
        sendEffect(EventDetailsUIEffect.NavigateBack)
    }

}
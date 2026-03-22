package com.uni.eventbridge.presentation.home

import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.presentation.common.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class HomeViewModel(
    private val eventRepository: EventRepository
) : BaseViewModel<HomeUiState, HomeUIEffect>(
    initialState = HomeUiState()
) {

    init {
        loadCategories()
        loadEvents()
    }

    private fun loadCategories() {
        tryToExecute(
            callee = {
                eventRepository.getCategory()
            },
            onSuccess = { categories ->
                updateState { it.copy(allCategories = categories.map { it.toUiState() }) }
            },
        )
    }

    private fun loadEvents() {
        val categoryId = currentState.selectedCategoryId

        tryToExecute(
            callee = {
                eventRepository.getEventByCategory(categoryId)
            },
            onStart = {
                updateState {
                    it.copy(isLoading = true)
                }
            },
            onSuccess = { events ->
                val joinedMap = coroutineScope {
                    events.map { event ->
                        async { event.id to eventRepository.isUserJoined(event.id) }
                    }.awaitAll().toMap()
                }
                updateState {
                    it.copy(
                        isLoading = false,
                        events = events.map { e ->
                            e.toUiState().copy(
                                isRegistered = joinedMap[e.id] == true,
                            )
                        },
                    )
                }
            },
            onError = {
                updateState { it.copy(isLoading = false) }
            },
        )
    }

    fun onCategorySelected(categoryId: Long?) {
        if (categoryId == currentState.selectedCategoryId) return
        updateState { it.copy(selectedCategoryId = categoryId) }
        loadEvents()
    }

    fun onEventClicked(eventId: Long) {
        sendEffect(HomeUIEffect.NavigateToEventDetails(eventId))
    }

    fun onJoinClick(eventId: Long) {
        val event = currentState.events.find { it.id == eventId } ?: return
        if (event.isFull && !event.isRegistered) {
            sendEffect(HomeUIEffect.ShowErrorSnackbar("Sorry, this event is full"))
            return
        }
        tryToExecute(
            callee = { eventRepository.joinEvent(eventId) },
            onStart = {
                updateState { s ->
                    s.copy(
                        events = s.events.map { e ->
                            if (e.id == eventId) e.copy(isLoading = true) else e
                        },
                    )
                }
            },
            onSuccess = {
                updateState { s ->
                    s.copy(
                        events = s.events.map { e ->
                            if (e.id != eventId) e
                            else {
                                val nextSeats = e.remainingSeats?.minus(1)
                                e.copy(
                                    isLoading = false,
                                    isRegistered = true,
                                    remainingSeats = nextSeats,
                                    isFull = nextSeats != null && nextSeats <= 0,
                                )
                            }
                        },
                    )
                }
                sendEffect(HomeUIEffect.ShowJoinSuccessSnackbar)
            },
            onError = {
                updateState { s ->
                    s.copy(
                        events = s.events.map { e ->
                            if (e.id == eventId) e.copy(isLoading = false) else e
                        },
                    )
                }
                if (event.remainingSeats != null && event.remainingSeats <= 0) {
                    sendEffect(HomeUIEffect.ShowErrorSnackbar("Sorry, this event is full"))
                } else {
                    sendEffect(HomeUIEffect.ShowErrorSnackbar("Could not join this event. Try again."))
                }
            },
        )
    }

    fun onLeaveClick(eventId: Long) {
        tryToExecute(
            callee = { eventRepository.leaveEvent(eventId) },
            onStart = {
                updateState { s ->
                    s.copy(
                        events = s.events.map { e ->
                            if (e.id == eventId) e.copy(isLoading = true) else e
                        },
                    )
                }
            },
            onSuccess = {
                updateState { s ->
                    s.copy(
                        events = s.events.map { e ->
                            if (e.id != eventId) e
                            else {
                                val nextSeats = e.remainingSeats?.plus(1)
                                e.copy(
                                    isLoading = false,
                                    isRegistered = false,
                                    remainingSeats = nextSeats,
                                    isFull = nextSeats != null && nextSeats <= 0,
                                )
                            }
                        },
                    )
                }
                sendEffect(HomeUIEffect.ShowLeaveSuccessSnackbar)
            },
            onError = {
                updateState { s ->
                    s.copy(
                        events = s.events.map { e ->
                            if (e.id == eventId) e.copy(isLoading = false) else e
                        },
                    )
                }
                sendEffect(HomeUIEffect.ShowErrorSnackbar("Failed to leave event. Please try again."))
            },
        )
    }
}

package com.uni.eventbridge.presentation.createEvent

import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class CreateEventViewModel(
    private val eventRepository: EventRepository,
) : BaseViewModel<CreateEventUiState, CreateEventUIEffect>(
    initialState = CreateEventUiState()
) {

    init {
        loadCategories()
    }

    private fun loadCategories() {
        tryToExecute(
            callee = {
                eventRepository.getCategory()
            },
            onSuccess = { categories ->
                updateState { state ->
                    state.copy(
                        allCategory = categories.map { it.toUiState() },
                    )
                }
            },
        )
    }
    fun onNextStep() {
        updateState { it.copy(validationError = null) }
        when (currentState.currentStep) {
            CreateEventUiState.Step.BASIC_INFO -> {
                if (!currentState.basicInfo.isComplete) {
                    updateState {
                        it.copy(
                            validationError = "Please fill in Event Title, add an Event Banner, and select a Category.",
                        )
                    }
                    return
                }
                updateState { it.copy(currentStep = CreateEventUiState.Step.DATE_AND_LOCATION) }
            }
            CreateEventUiState.Step.DATE_AND_LOCATION -> {
                if (!currentState.dateAndLocation.isComplete) {
                    updateState {
                        it.copy(
                            validationError = "Please set Event Date, Start Time, End Time, and Location.",
                        )
                    }
                    return
                }
                updateState { it.copy(currentStep = CreateEventUiState.Step.REVIEW) }
            }
            CreateEventUiState.Step.REVIEW -> { /* no-op */ }
        }
    }

    fun onBackClicked() {
        if (currentState.isFirstStep) {
            sendEffect(CreateEventUIEffect.NavigateBack)
        } else {
            val prev = when (currentState.currentStep) {
                CreateEventUiState.Step.DATE_AND_LOCATION -> CreateEventUiState.Step.BASIC_INFO
                CreateEventUiState.Step.REVIEW -> CreateEventUiState.Step.DATE_AND_LOCATION
                CreateEventUiState.Step.BASIC_INFO -> return
            }
            updateState { it.copy(currentStep = prev) }
        }
    }

    fun onCancelClicked() {
        sendEffect(CreateEventUIEffect.NavigateBack)
    }


    fun onEditBasicInfo() {
        updateState { it.copy(currentStep = CreateEventUiState.Step.BASIC_INFO) }
    }

    fun onEditDateAndLocation() {
        updateState { it.copy(currentStep = CreateEventUiState.Step.DATE_AND_LOCATION) }
    }


    fun onBannerSelected(bytes: ByteArray?) {
        updateState {
            it.copy(
                basicInfo = it.basicInfo.copy(bannerBytes = bytes),
                validationError = null,
            )
        }
    }

    fun onTitleChanged(title: String) {
        updateState {
            it.copy(
                basicInfo = it.basicInfo.copy(title = title),
                validationError = null,
            )
        }
    }

    fun onCategoryChanged(categoryId: Long) {
        updateState {
            it.copy(
                basicInfo = it.basicInfo.copy(categoryId = categoryId),
                validationError = null,
            )
        }
    }

    fun onDepartmentChanged(department: String) {
        updateState { it.copy(basicInfo = it.basicInfo.copy(department = department)) }
    }

    fun onDescriptionChanged(description: String) {
        updateState { it.copy(basicInfo = it.basicInfo.copy(description = description)) }
    }

    fun onDateSelected(date: String) {
        updateState {
            it.copy(
                dateAndLocation = it.dateAndLocation.copy(date = date),
                validationError = null,
            )
        }
    }

    fun onStartTimeChanged(time: String) {
        updateState {
            it.copy(
                dateAndLocation = it.dateAndLocation.copy(startTime = time),
                validationError = null,
            )
        }
    }

    fun onEndTimeChanged(time: String) {
        updateState {
            it.copy(
                dateAndLocation = it.dateAndLocation.copy(endTime = time),
                validationError = null,
            )
        }
    }

    fun onLocationChanged(location: String) {
        updateState {
            it.copy(
                dateAndLocation = it.dateAndLocation.copy(location = location),
                validationError = null,
            )
        }
    }

    fun onMapPinned(lat: Double, lng: Double) {
        updateState {
            it.copy(
                dateAndLocation = it.dateAndLocation.copy(
                    pinLatitude = lat,
                    pinLongitude = lng
                )
            )
        }
    }


    fun onPublishClicked() {
        if (!currentState.canPublish) {
            val errors = mutableListOf<String>()
            if (!currentState.basicInfo.isComplete) {
                errors.add("Complete Basic Info: title, banner image, and category.")
            }
            if (!currentState.dateAndLocation.isComplete) {
                errors.add("Complete Date & Location: date, start time, end time, and location.")
            }
            updateState {
                it.copy(validationError = errors.joinToString(" "))
            }
            return
        }
        updateState { it.copy(validationError = null) }
        tryToExecute(
            callee = {
                eventRepository.createEvent(currentState.toCreateEventRequest())
            },
            onStart = {
                updateState { it.copy(isLoading = true) }
            },
            onSuccess = {
                updateState { it.copy(isLoading = false) }
                sendEffect(CreateEventUIEffect.EventPublished)
            },
            onError = {
                updateState { it.copy(isLoading = false) }
            },
        )
    }
}
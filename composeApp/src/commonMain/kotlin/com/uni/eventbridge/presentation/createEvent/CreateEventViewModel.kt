package com.uni.eventbridge.presentation.createEvent

import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class CreateEventViewModel(
    private val eventRepository: EventRepository,
) : BaseViewModel<CreateEventUiState, CreateEventUIEffect>(
    initialState = CreateEventUiState()
) {

    fun onNextStep() {
        val next = when (currentState.currentStep) {
            CreateEventUiState.Step.BASIC_INFO -> CreateEventUiState.Step.DATE_AND_LOCATION
            CreateEventUiState.Step.DATE_AND_LOCATION -> CreateEventUiState.Step.REVIEW
            CreateEventUiState.Step.REVIEW -> return
        }
        updateState { it.copy(currentStep = next) }
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
        updateState { it.copy(basicInfo = it.basicInfo.copy(bannerBytes = bytes)) }
    }

    fun onTitleChanged(title: String) {
        updateState { it.copy(basicInfo = it.basicInfo.copy(title = title)) }
    }

    fun onCategoryChanged(category: String) {
        updateState { it.copy(basicInfo = it.basicInfo.copy(category = category)) }
    }

    fun onDepartmentChanged(department: String) {
        updateState { it.copy(basicInfo = it.basicInfo.copy(department = department)) }
    }

    fun onDescriptionChanged(description: String) {
        updateState { it.copy(basicInfo = it.basicInfo.copy(description = description)) }
    }

    fun onDateSelected(date: String) {
        updateState { it.copy(dateAndLocation = it.dateAndLocation.copy(date = date)) }
    }

    fun onStartTimeChanged(time: String) {
        updateState { it.copy(dateAndLocation = it.dateAndLocation.copy(startTime = time)) }
    }

    fun onEndTimeChanged(time: String) {
        updateState { it.copy(dateAndLocation = it.dateAndLocation.copy(endTime = time)) }
    }

    fun onLocationChanged(location: String) {
        updateState { it.copy(dateAndLocation = it.dateAndLocation.copy(location = location)) }
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
        tryToExecute(
            callee = {
                eventRepository.createEvent(currentState.toCreateEventRequest())
            },
            onStart = {
                updateState { it.copy(isLoading = true) }
            },
            onSuccess = {
                updateState { it.copy(isLoading = false) }
            },
            onError = {
                updateState { it.copy(isLoading = false) }
            },
        )
    }
}
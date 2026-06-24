package com.uni.eventbridge.presentation.createEvent

import com.uni.eventbridge.domain.model.Location
import com.uni.eventbridge.domain.model.MapRoute

data class CreateEventUiState(
    val isLoading: Boolean = false,
    val currentStep: Step = Step.BASIC_INFO,
    val basicInfo: BasicInfoUiState = BasicInfoUiState(),
    val dateAndLocation: DateAndLocationUiState = DateAndLocationUiState(),
    val allCategory: List<CategoryUiState> = emptyList(),
    val validationError: String? = null,
    val route: MapRoute? = null,
) {
    enum class Step { BASIC_INFO, DATE_AND_LOCATION, REVIEW }

    data class BasicInfoUiState(
        val bannerBytes: ByteArray? = null,
        val title: String? = null,
        val categoryId: Long = 0L,
        val description: String? = null,
        val department: String? = null,
    ) {
        val isComplete: Boolean
            get() = !title.isNullOrBlank() &&
                    bannerBytes != null && bannerBytes.isNotEmpty() && categoryId != 0L

    }

    data class CategoryUiState(
        val id: Long = 0L,
        val name: String? = null
    )

    data class DateAndLocationUiState(
        val date: String? = null,
        val startTime: String? = null,
        val endTime: String? = null,
        val location: String? = null,
        val pinnedLocation: Location? = null,
        val userLocation: Location? = null
    ) {
        val pinLatitude: Double? get() = pinnedLocation?.latitude
        val pinLongitude: Double? get() = pinnedLocation?.longitude
        val isComplete: Boolean
            get() = !(date.isNullOrBlank() &&
                    startTime.isNullOrBlank() &&
                    endTime.isNullOrBlank() &&
                    location.isNullOrBlank())
    }

    val currentStepIndex: Int get() = Step.entries.indexOf(currentStep)
    val totalSteps: Int get() = Step.entries.size
    val progressFraction: Float get() = (currentStepIndex + 1) / totalSteps.toFloat()
    val isLastStep: Boolean get() = currentStep == Step.REVIEW
    val isFirstStep: Boolean get() = currentStep == Step.BASIC_INFO
    val canPublish: Boolean
        get() = basicInfo.isComplete && dateAndLocation.isComplete
}
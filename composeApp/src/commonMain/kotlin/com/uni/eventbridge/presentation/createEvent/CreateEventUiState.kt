package com.uni.eventbridge.presentation.createEvent

data class CreateEventUiState(
    val isLoading: Boolean = false,
    val currentStep: Step = Step.BASIC_INFO,
    val basicInfo: BasicInfoUiState = BasicInfoUiState(),
    val dateAndLocation: DateAndLocationUiState = DateAndLocationUiState(),
    val allCategory: List<CategoryUiState> =emptyList(),
    val validationError: String? = null,
) {
    enum class Step { BASIC_INFO, DATE_AND_LOCATION, REVIEW }

    data class BasicInfoUiState(
        val bannerBytes: ByteArray? = null,
        val title: String = "",
        val categoryId: Long = 0L,
        val description: String = "",
        val department: String = "",
    ) {
        val isComplete: Boolean
            get() = title.isNotBlank() &&
                bannerBytes != null && bannerBytes.isNotEmpty() && categoryId != 0L

    }

    data class CategoryUiState(
        val id: Long = 0L,
        val name: String = ""
    )
    data class DateAndLocationUiState(
        val date: String = "",
        val startTime: String = "",
        val endTime: String = "",
        val location: String = "",
        val pinLatitude: Double? = null,
        val pinLongitude: Double? = null,
    ) {
        val isComplete: Boolean
            get() = date.isNotBlank() &&
                startTime.isNotBlank() &&
                endTime.isNotBlank() &&
                location.isNotBlank()
    }

    val currentStepIndex: Int get() = Step.entries.indexOf(currentStep)
    val totalSteps: Int get() = Step.entries.size - 1 // REVIEW is not counted as a form step
    val progressFraction: Float get() = (currentStepIndex + 1) / totalSteps.toFloat()
    val isLastStep: Boolean get() = currentStep == Step.DATE_AND_LOCATION
    val isFirstStep: Boolean get() = currentStep == Step.BASIC_INFO
    val canPublish: Boolean
        get() = basicInfo.isComplete && dateAndLocation.isComplete
}
package com.uni.eventbridge.presentation.createEvent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uni.eventbridge.presentation.createEvent.component.BasicInfoStep
import com.uni.eventbridge.presentation.createEvent.component.ReviewStep
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onEventPublished: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is CreateEventUIEffect.NavigateBack -> onNavigateBack()
                is CreateEventUIEffect.EventPublished -> onEventPublished()
                is CreateEventUIEffect.NavigateToReview -> {}
            }
        }
    }

    when (uiState.currentStep) {
        CreateEventUiState.Step.BASIC_INFO -> BasicInfoStep(
            uiState = uiState.basicInfo,
            currentStepIndex = uiState.currentStepIndex,
            totalSteps = uiState.totalSteps,
            onBannerSelected = viewModel::onBannerSelected,
            onTitleChanged = viewModel::onTitleChanged,
            onCategoryChanged = viewModel::onCategoryChanged,
            onDepartmentChanged = viewModel::onDepartmentChanged,
            onNextStep = viewModel::onNextStep,
            onBackClicked = viewModel::onBackClicked,
            onCancelClicked = viewModel::onCancelClicked,
        )

        CreateEventUiState.Step.DATE_AND_LOCATION -> DateAndLocationStep(
            uiState = uiState.dateAndLocation,
            currentStepIndex = uiState.currentStepIndex,
            totalSteps = uiState.totalSteps,
            onDateSelected = viewModel::onDateSelected,
            onStartTimeChanged = viewModel::onStartTimeChanged,
            onEndTimeChanged = viewModel::onEndTimeChanged,
            onLocationChanged = viewModel::onLocationChanged,
            onMapPinned = viewModel::onMapPinned,
            onNextStep = viewModel::onNextStep,
            onBackClicked = viewModel::onBackClicked,
        )

        CreateEventUiState.Step.REVIEW -> ReviewStep(
            uiState = uiState,
            onBackClicked = viewModel::onBackClicked,
            onPublishClicked = viewModel::onPublishClicked,
            onEditBasicInfo = viewModel::onEditBasicInfo,
            onEditDateAndLocation = viewModel::onEditDateAndLocation,
        )
    }
}
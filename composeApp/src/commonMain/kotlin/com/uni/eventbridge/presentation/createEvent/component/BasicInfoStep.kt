package com.uni.eventbridge.presentation.createEvent.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.uni.eventbridge.presentation.common.component.EventBridgeScaffold
import com.uni.eventbridge.presentation.common.component.EventTextField
import com.uni.eventbridge.presentation.common.component.PrimaryButton
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.Secondary
import com.uni.eventbridge.presentation.common.component.theme.White
import com.uni.eventbridge.presentation.common.component.topBar.DefaultTopBar
import com.uni.eventbridge.presentation.createEvent.CreateEventUiState
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_add_image
import org.jetbrains.compose.resources.painterResource

@Composable
fun BasicInfoStep(
    uiState: CreateEventUiState.BasicInfoUiState,
    currentStepIndex: Int,
    totalSteps: Int,
    categories: List<CreateEventUiState.CategoryUiState>,
    validationError: String? = null,
    onBannerSelected: (ByteArray?) -> Unit,
    onTitleChanged: (String) -> Unit,
    onCategoryChanged: (Long) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onNextStep: () -> Unit,
    onBackClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
    EventBridgeScaffold(
        topBar = {
            DefaultTopBar(
                title = "Create Event",
                onBackClick = onBackClicked,
                actionLabel = "Cancel",
                onActionClick = onCancelClicked,
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth().background(color = White)
            ) {
                PrimaryButton(
                    label = "Next Step",
                    onClick = onNextStep,
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                StepProgressHeader(
                    stepLabel = "Basic Info",
                    stepSublabel = "${currentStepIndex + 1} of $totalSteps",
                    progressFraction = (currentStepIndex + 1) / totalSteps.toFloat(),
                )

                if (validationError != null) {
                    Text(
                        text = validationError,
                        fontSize = 13.sp,
                        color = Color(0xFFB00020),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0x1AB00020), RoundedCornerShape(8.dp))
                            .padding(12.dp),
                    )
                }

                BannerPicker(
                    bannerBytes = uiState.bannerBytes,
                    onBannerSelected = onBannerSelected,
                )

                StepTextField(
                    value = uiState.title.orEmpty(),
                    onValueChange = onTitleChanged,
                    label = "Event Title",
                    hint = "e.g., Annual Tech Symposium",
                )

                CategoryDropdown(
                    categories = categories,
                    selected = uiState.categoryId,
                    onCategorySelected = onCategoryChanged,
                )

                StepTextField(
                    value = uiState.description.orEmpty(),
                    onValueChange = onDescriptionChanged,
                    label = "Description",
                    hint = "e.g., Computer Science Department",
                    maxLines = 5,
                    modifier = Modifier.heightIn(min = 120.dp)
                )
            }
        }

    }
}


@Composable
private fun BannerPicker(
    bannerBytes: ByteArray?,
    onBannerSelected: (ByteArray?) -> Unit,
) {
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = rememberCoroutineScope(),
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { onBannerSelected(it) }
        },
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(288.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF4F6FA))
            .border(
                width = 1.5.dp,
                color = Color(0xFFCCCCCC),
                shape = RoundedCornerShape(12.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (bannerBytes != null && bannerBytes.isNotEmpty()) {
            AsyncImage(
                model = bannerBytes,
                contentDescription = "Event Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,
            ) {
                Button(
                    onClick = { singleImagePicker.launch() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8EEFF)),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text("Change Image", color = Primary, fontSize = 13.sp)
                }
            }
        } else {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add_image),
                    contentDescription = null,
                    tint = Color(0xFFAAAAAA),
                    modifier = Modifier.size(40.dp),
                )
                Text(
                    text = "Add Event Banner",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF333333),
                )
                Text(
                    text = "Upload a high-quality cover photo (16:9 recommended)",
                    fontSize = 11.sp,
                    color = Color(0xFF888888),
                )
                Button(
                    onClick = { singleImagePicker.launch() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8EEFF)),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text("Select Image", color = Primary, fontSize = 13.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    categories: List<CreateEventUiState.CategoryUiState>,
    selected: Long,
    onCategorySelected: (Long) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = "Category",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            OutlinedTextField(
                value = categories.firstOrNull { it.id == selected }?.name
                        ?: if (categories.isEmpty())
                            "No categories available"
                        else
                            "Select event category",
                onValueChange = {},
                readOnly = true,
                enabled = categories.isNotEmpty(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Color(0xFFD1D5DB),
                    disabledBorderColor = Secondary,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                )
            )
            if (categories.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = Secondary,
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name.orEmpty()) },
                            onClick = {
                                onCategorySelected(category.id)
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StepTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    hint: String = "",
    maxLines: Int = 1,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
        )

        EventTextField(
            value = value,
            onValueChange = onValueChange,
            hint = hint,
            modifier = modifier.fillMaxWidth(),
            maxLines = maxLines,
        )
    }
}
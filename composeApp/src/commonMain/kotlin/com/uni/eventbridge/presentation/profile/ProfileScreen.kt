package com.uni.eventbridge.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.uni.eventbridge.designSystem.topBar.DefaultTopBar
import com.uni.eventbridge.presentation.common.component.EventBridgeScaffold
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.Secondary
import com.uni.eventbridge.presentation.common.component.theme.White
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_arrow_right
import eventbridge.composeapp.generated.resources.ic_doc
import eventbridge.composeapp.generated.resources.ic_explore
import eventbridge.composeapp.generated.resources.ic_message
import eventbridge.composeapp.generated.resources.ic_out
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToContactUs: () -> Unit = {},
    onNavigateToTermsOfService: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ProfileUIEffect.NavigateToSignIn -> onNavigateToSignIn()
                is ProfileUIEffect.NavigateToContactUs -> onNavigateToContactUs()
                is ProfileUIEffect.NavigateToTermsOfService -> onNavigateToTermsOfService()
            }
        }
    }

    ProfileContent(
        uiState = uiState,
        onContactUsClicked = viewModel::onContactUsClicked,
        onTermsOfServiceClicked = viewModel::onTermsOfServiceClicked,
        onSignOutClicked = viewModel::onSignOutClicked,
        onSignOutConfirmed = viewModel::onSignOutConfirmed,
        onSignOutDismissed = viewModel::onSignOutDismissed,
    )
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState = ProfileUiState(),
    onContactUsClicked: () -> Unit = {},
    onTermsOfServiceClicked: () -> Unit = {},
    onSignOutClicked: () -> Unit = {},
    onSignOutConfirmed: () -> Unit = {},
    onSignOutDismissed: () -> Unit = {},
) {
    EventBridgeScaffold(
        isLoading = uiState.isLoading,
        topBar = {
            DefaultTopBar(
                title = "Profile",
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFF1F5F9),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Secondary),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = uiState.avatarUrl,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Text(
                text = uiState.fullName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A2E),
                modifier = Modifier.padding(top = 16.dp)
            )



            Spacer(modifier = Modifier.height(32.dp))

            ProfileAccountDetails(
                onContactUsClicked = onContactUsClicked,
                onTermsOfServiceClicked = onTermsOfServiceClicked,
            )

            HorizontalDivider(
                color = Color(0xFFF0F0F0),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            ProfileSignOutRow(onSignOutClicked = onSignOutClicked)

        }

        if (uiState.showSignOutDialog) {
            SignOutDialog(
                onConfirm = onSignOutConfirmed,
                onDismiss = onSignOutDismissed,
            )
        }
    }
}

@Composable
private fun ProfileAccountDetails(
    onContactUsClicked: () -> Unit,
    onTermsOfServiceClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "ACCOUNT DETAILS",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF9E9E9E),
            letterSpacing = 1.sp,
        )


        ProfileMenuItem(
            iconRes = Res.drawable.ic_message,
            title = "Contact Us",
            subtitle = "support@unievents.edu",
            onClick = onContactUsClicked,
        )


        ProfileMenuItem(
            iconRes = Res.drawable.ic_doc,
            title = "Terms of Service",
            onClick = onTermsOfServiceClicked,
        )
    }
}

@Composable
private fun ProfileMenuItem(
    iconRes: org.jetbrains.compose.resources.DrawableResource,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val clipboard = LocalClipboardManager.current

        Box(
            modifier = Modifier
                .clickable {
                    subtitle?.let {
                        clipboard.setText(AnnotatedString(it))
                    }
                    onClick()
                }
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEEF2FF)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = title,
                tint = Primary,
                modifier = Modifier.size(18.dp),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1A1A2E),
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E),
                )
            }
        }

        Icon(
            painter = painterResource(Res.drawable.ic_arrow_right),
            contentDescription = null,
            tint = Color(0xFFBDBDBD),
            modifier = Modifier.size(16.dp),
        )
    }
}

@Composable
private fun ProfileSignOutRow(onSignOutClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSignOutClicked)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFFFEEEE)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_out),
                contentDescription = "Sign Out",
                tint = Color(0xFFE53935),
                modifier = Modifier.size(18.dp),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Sign Out",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFE53935),
        )
    }
}

@Composable
private fun SignOutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = White,
        shape = RoundedCornerShape(20.dp),
        icon = {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEEF2FF)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_explore),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(28.dp),
                )
            }
        },
        title = {
            Text(
                text = "Sign Out?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A2E),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        text = {
            Text(
                text = "Are you sure you want to sign out of your account?",
                fontSize = 14.sp,
                color = Color(0xFF9E9E9E),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
            ) {
                Text(
                    text = "Sign Out",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = White,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A1A2E),
                )
            }
        },
    )
}

@Preview
@Composable
private fun ProfilscreenPreview() {
    ProfileContent()
}
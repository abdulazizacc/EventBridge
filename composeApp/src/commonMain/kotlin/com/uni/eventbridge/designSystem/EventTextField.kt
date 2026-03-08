package com.uni.eventbridge.designSystem

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.designSystem.modifier.noRippleClickable
import com.uni.eventbridge.designSystem.theme.Primary
import com.uni.eventbridge.designSystem.theme.White
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_explore
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun EventTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    leadingIcon: Painter? = null,
    maxLines: Int = 1,
    maxCharacter: Int = 32
) {
    var isFocused by remember { mutableStateOf(false) }
    val textFieldFocusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isFocused) Primary
        else Color(0xFFD1D5DB),
        animationSpec = tween(300)
    )
    val animatedContentColor by animateColorAsState(
        targetValue = if (value.isNotBlank()) Primary
        else Color(0xFF94A3B8),
        animationSpec = tween(300)
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(
                border = BorderStroke(1.dp, animatedBorderColor),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(start = 12.dp, top = 13.dp, bottom = 13.dp)
            .noRippleClickable {
                scope.launch {
                    textFieldFocusRequester.requestFocus()
                }
            }
    ) {
        leadingIcon?.let { icon ->
            Icon(
                painter = icon,
                contentDescription = null,
                tint = animatedContentColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .then(
                    if (leadingIcon == null) Modifier.padding(top = 5.dp)
                    else Modifier.align(Alignment.CenterVertically)
                )
        ) {
            if (value.isEmpty() && !isFocused) {
                Text(
                    text = hint,
                    color = Color(0xFF94A3B8),
                )
            }
            BasicTextField(
                value = value,
                onValueChange = { input ->
                    input.takeIf { it.length <= maxCharacter }?.let(onValueChange)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }.focusRequester(textFieldFocusRequester),
                maxLines = maxLines,
                readOnly = readOnly,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EventTextFieldPreview(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    EventTextField(
        value = name,
        onValueChange = { name = it },
        hint = "what a hint",
        modifier = modifier,
        leadingIcon = painterResource(Res.drawable.ic_explore)
    )
}
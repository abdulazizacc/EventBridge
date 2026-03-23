package com.uni.eventbridge.presentation.common.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.presentation.common.component.modifier.noRippleClickable
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.Secondary
import com.uni.eventbridge.presentation.common.component.theme.SlateGray
import com.uni.eventbridge.presentation.common.component.theme.White


@Composable
@Preview
fun EventChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = true,
    onClick: () -> Unit = {},
    title: String= "chip"
){

    val transition = updateTransition(
        targetState = isSelected
    )
    val backgroundColor by transition.animateColor(
        targetValueByState = { if (it) Primary else Secondary },
        transitionSpec = { TweenSpec(durationMillis = 300) }
    )
    val textColor by transition.animateColor(
        targetValueByState = { if (it) White else SlateGray },
        transitionSpec = { TweenSpec(durationMillis = 300) },
    )
    Text(
        text = title,
        color = textColor,
        modifier = modifier
            .noRippleClickable { onClick() }
            .background(color = backgroundColor, shape = RoundedCornerShape(100))
            .padding(vertical = 8.dp, horizontal = 20.dp)
    )
}


package com.uni.eventbridge.designSystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uni.eventbridge.designSystem.theme.Secondary
import com.uni.eventbridge.designSystem.theme.SlateGray
import com.uni.eventbridge.designSystem.theme.White
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_calendder
import eventbridge.composeapp.generated.resources.ic_location
import eventbridge.composeapp.generated.resources.im_banner_image
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun EventCard(
    title: String,
    date: String,
    location: String,
    imageRes: DrawableResource = Res.drawable.im_banner_image,
    modifier: Modifier = Modifier,

    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp,color = Secondary,shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .padding(top = 13.dp, bottom = 13.dp, start = 13.dp)
                .clip(RoundedCornerShape(8.dp)),
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 13.dp)
                .align(Alignment.Top)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_calendder),
                    contentDescription = null,
                    tint = SlateGray,
                    modifier = Modifier.size(12.dp).padding(end = 4.dp)
                )
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = SlateGray,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_location),
                    contentDescription = null,
                    tint = SlateGray,
                    modifier = Modifier.size(12.dp).padding(end = 4.dp)
                )
                Text(
                    text = location,
                    fontSize = 12.sp,
                    color = SlateGray,
                )
            }
        }
    }
}

@Preview
@Composable
private fun EventCardPreview() {
    EventCard(
        title = "AI & Ethics Workshop",
        date = "Oct 26, 2023 • 2:30 PM",
        location = "sammaarra",
    )
}
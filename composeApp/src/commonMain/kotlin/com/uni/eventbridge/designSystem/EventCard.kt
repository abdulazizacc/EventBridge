package com.uni.eventbridge.designSystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.uni.eventbridge.designSystem.theme.Red
import com.uni.eventbridge.designSystem.theme.SlateGray
import com.uni.eventbridge.designSystem.theme.SurfaceDark
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_location
import eventbridge.composeapp.generated.resources.im_banner_image
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun EventCard(
    imageRes: DrawableResource = Res.drawable.im_banner_image,
    date: String,
    title: String,
    location: String,
    category: String? = null,
    onClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(191.dp)
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                )

                category?.let {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = category.uppercase(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2979FF),
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = date,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Red,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SurfaceDark,
                    lineHeight = 24.sp
                )

                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_location),
                        contentDescription = null,
                        tint = SlateGray,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = location,
                        fontSize = 13.sp,
                        color = SlateGray,
                    )
                }

                PrimaryButton(
                    onClick = onRegisterClick,
                    label = "Register"
                )
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    EventCard(
        imageRes = Res.drawable.im_banner_image,
        date = "FRI, OCT 25 • 6:00 PM",
        title = "Annual Spring Hackathon",
        location = "Student Union Hall, Main Campus",
        category = "Trending",
        onRegisterClick = { /* navigate */ }
    )
}
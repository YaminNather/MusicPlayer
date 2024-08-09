package com.example.musicplayer.screens.maximizedsongplayer

import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.neumorphism.core.modifiers.neumorphic

@Composable
fun RecordPlayer(progress: Float, coverArt: Uri?) {
    val transition = rememberInfiniteTransition("Rotating Animation")
    val rotation by transition.animateFloat(
        label = "Rotation Animation",
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable( tween(5000, easing = LinearEasing) ),
    )

    BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
        val diameter: Dp = min(460.dp, min(maxWidth * 2, maxHeight))

        Box(
            modifier = Modifier
                .requiredSize(diameter)
                .offset(x = maxWidth / 2, y = (maxHeight - diameter) / 2)
                .neumorphic(shape = RoundedCornerShape(3000.dp))
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .neumorphic(shape = RoundedCornerShape(3000.dp), height = (-4).dp)
                    .padding(24.dp)
            ) {
                ProgressBar(value = progress, modifier = Modifier.padding(24.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .neumorphic(shape = RoundedCornerShape(3000.dp))
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3000.dp))
                                .fillMaxSize()
//                                .background(Color(0xFF6b7892))
                                .rotate(rotation)
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(R.drawable.songcover),
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}

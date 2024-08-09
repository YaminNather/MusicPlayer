package screens.home.components.songrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SongRack(modifier: Modifier = Modifier, category: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            category,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.headlineLarge
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.Gray)
        )
    }
}
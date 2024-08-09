package com.example.musicplayer.components.customlisttile

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
internal fun CustomListTile(
    modifier: Modifier = Modifier,
    headlineContent: @Composable () -> Unit,
    supportingContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    isHighlighted: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .neumorphic(
                height = if (isHighlighted) (-4).dp else 0.dp,
                shape = RoundedCornerShape(16.dp),
                backgroundColor = if (isHighlighted) Color(0xFFA9C2EC) else Color.Transparent,
            )
            .clickable { onClick?.invoke() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingContent?.invoke()

        if (leadingContent != null) {
            Spacer(Modifier.width(16.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            headlineContent()

            if (supportingContent != null) {
                Spacer(Modifier.height(4.dp))
            }


            CompositionLocalProvider(
                LocalTextStyle.provides(MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
            ) {
                supportingContent?.invoke()
            }
        }

        if (trailingContent != null) Spacer(Modifier.width(16.dp))

        trailingContent?.invoke()
    }
}

@Preview
@Composable
private fun CustomListTilePreview() {
    RootThemeProvider {
        val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
                items(20) { index ->
                    CustomListTile(
                        modifier = Modifier.padding(top = 16.dp).fillMaxWidth().wrapContentSize(),
                        headlineContent = { Text("Headline") },
                        supportingContent = { Text("Supporting Headline") },
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .neumorphic(shape = RoundedCornerShape(3000.dp)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(Icons.Default.MusicNote, null, tint = theme.resolvedColorScheme().text)
                            }
                        },
                        trailingContent = {
                            IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) }
                        },
                        isHighlighted = index == 2,
                    )
                }
            }
        }
    }
}
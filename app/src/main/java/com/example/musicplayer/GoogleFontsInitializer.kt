package com.example.musicplayer

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont

public class GoogleFontsInitializer {
    public companion object {
        public fun initialize(): Unit {
            val provider: GoogleFont.Provider = GoogleFont.Provider(
                providerAuthority = "com.google.android.gms.fonts",
                providerPackage = "com.google.android.gms",
                certificates = R.array.com_google_android_gms_fonts_certs,
            )

            val fontName: GoogleFont = GoogleFont("Lobster Two")

            val fontFamily: FontFamily = FontFamily(
                Font(
                    googleFont = fontName,
                    fontProvider = provider,
                    weight = FontWeight.Bold,
                )
            )
        }
    }
}
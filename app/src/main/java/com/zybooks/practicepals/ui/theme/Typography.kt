package com.zybooks.practicepals.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.zybooks.practicepals.R

val AppTypography = Typography(
    // Define your custom text styles here if needed, or use defaults
    // Example for title, body, etc.
    bodyLarge = Typography().bodyLarge.copy(fontFamily = FontFamily.Default),
    titleLarge = Typography().titleLarge.copy(fontFamily = FontFamily.Default)
)

package com.baratali.cnote.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF7F67F0),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF514BA3),
    onPrimaryContainer = Color(0xFFE6E0FF),
    inversePrimary = Color(0xFF6200EE),

    secondary = Color(0xFF4E4E4E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF3A3A3A),
    onSecondaryContainer = Color(0xFFCFCFCF),

    tertiary = Color(0xFF1F1F1F),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF373737),
    onTertiaryContainer = Color(0xFFD5D5D5),

    background = Color(0xFF121212),
    onBackground = Color(0xFFE6E6E6),

    surface = Color(0xFF2E2E2E),
    onSurface = Color(0xFFE6E6E6),
    surfaceVariant = Color(0xFF424242),
    onSurfaceVariant = Color(0xFFCFCFCF),

    surfaceTint = Color(0xFF7F67F0),
    inverseSurface = Color(0xFFE6E6E6),
    inverseOnSurface = Color(0xFF1A1A1A),

    error = Color(0xFFCF6679),
    onError = Color.White,
    errorContainer = Color(0xFF601410),
    onErrorContainer = Color(0xFFFFDAD4),

    outline = Color(0xFF8A8A8A),
    outlineVariant = Color(0xFF515151),
    scrim = Color.Black,

    surfaceBright = Color(0xFF3C3C3C),
    surfaceContainer = Color(0xFF2E2E2E),
    surfaceContainerHigh = Color(0xFF383838),
    surfaceContainerHighest = Color(0xFF404040),
    surfaceContainerLow = Color(0xFF242424),
    surfaceContainerLowest = Color(0xFF1A1A1A),
    surfaceDim = Color(0xFF212121)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBB86FC),
    onPrimaryContainer = Color(0xFF3700B3),
    inversePrimary = Color(0xFF7F67F0),

    secondary = Color(0xFFF3F3F3),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFE0E0E0),
    onSecondaryContainer = Color(0xFF404040),

    tertiary = Color(0xFFFFFFFF),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFE6E6E6),
    onTertiaryContainer = Color(0xFF303030),

    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF121212),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF121212),
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF404040),

    surfaceTint = Color(0xFF6200EE),
    inverseSurface = Color(0xFF1A1A1A),
    inverseOnSurface = Color(0xFFE6E6E6),

    error = Color(0xFFB00020),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD4),
    onErrorContainer = Color(0xFF410002),

    outline = Color(0xFF8A8A8A),
    outlineVariant = Color(0xFFCFCFCF),
    scrim = Color.Black,

    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainer = Color(0xFFF5F5F5),
    surfaceContainerHigh = Color(0xFFEBEBEB),
    surfaceContainerHighest = Color(0xFFE0E0E0),
    surfaceContainerLow = Color(0xFFF0F0F0),
    surfaceContainerLowest = Color(0xFFF8F8F8),
    surfaceDim = Color(0xFFECECEC)
)

val typography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Content
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Content
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Content
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Content
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Content
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Content
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Content
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        textDirection = TextDirection.Content
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        textDirection = TextDirection.Content
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        textDirection = TextDirection.Content
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        textDirection = TextDirection.Content
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        textDirection = TextDirection.Content
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        textDirection = TextDirection.Content
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        textDirection = TextDirection.Content
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        textDirection = TextDirection.Content
    )
)

@Composable
fun CNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

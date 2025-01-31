package com.baratali.cnote.feature_task.data.data_source.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalMall
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Nature
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.Work
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryIcon(val imageVector: ImageVector) {
    WORK(Icons.Outlined.Work),
    GROCERY(Icons.Outlined.ShoppingCart),
    SPORT(Icons.Outlined.FitnessCenter),
    DESIGN(Icons.Outlined.Brush),
    UNIVERSITY(Icons.Outlined.School),
    SOCIAL(Icons.Outlined.Campaign),
    MUSIC(Icons.Outlined.MusicNote),
    HEALTH(Icons.Outlined.Favorite),
    MOVIE(Icons.Outlined.Movie),
    HOME(Icons.Outlined.Home),
    GAME(Icons.Outlined.SportsEsports),
    TRAVEL(Icons.Outlined.Flight),
    BOOK(Icons.Outlined.MenuBook),
    STUDY(Icons.Outlined.AutoStories),
    CODE(Icons.Outlined.Code),
    PARTY(Icons.Outlined.Celebration),
    EXERCISE(Icons.Outlined.DirectionsRun),
    SHOPPING(Icons.Outlined.LocalMall),
    FOOD(Icons.Outlined.Fastfood),
    PET(Icons.Outlined.Pets),
    NATURE(Icons.Outlined.Nature),
    CAR(Icons.Outlined.DirectionsCar),
    DOCUMENT(Icons.Outlined.Description),
    MONEY(Icons.Outlined.AttachMoney),
    DEFAULT(Icons.Outlined.Category);
    companion object {
        fun fromName(name: String): CategoryIcon {
            return values().find { it.name.equals(name, ignoreCase = true) } ?: DEFAULT
        }
    }
}

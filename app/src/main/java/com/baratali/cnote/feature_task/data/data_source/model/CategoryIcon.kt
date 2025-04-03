package com.baratali.cnote.feature_task.data.data_source.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.ui.graphics.vector.ImageVector
import com.baratali.cnote.R
import com.baratali.cnote.feature_task.data.data_source.model.CategoryIcon.values

enum class CategoryIcon(
    @DrawableRes val iconRes: Int? = null,
    val imageVector: ImageVector? = null
) {
    WORK(R.drawable.ic_case),
    SPORT(imageVector = Icons.Outlined.FitnessCenter),
    GROCERY(R.drawable.ic_shopping_cart),
    DESIGN(R.drawable.ic_brush),
    UNIVERSITY(R.drawable.ic_teacher),
    SOCIAL(imageVector = Icons.Outlined.Campaign),
    MUSIC(R.drawable.ic_music),
    HEALTH(R.drawable.ic_heart),
    MOVIE(R.drawable.ic_video_play),
    HOME(R.drawable.ic_house),
    GAME(R.drawable.ic_game),
    TRAVEL(R.drawable.ic_airplane),
    BOOK(R.drawable.ic_book),
    CODE(R.drawable.ic_code),
    PARTY(imageVector = Icons.Outlined.Celebration),
    FOOD(imageVector = Icons.Outlined.Fastfood),
    PET(R.drawable.ic_pet),
    NATURE(R.drawable.ic_tree),
    CAR(R.drawable.ic_car),
    DOCUMENT(R.drawable.ic_document),
    MONEY(R.drawable.ic_dollar),
    DEFAULT(R.drawable.ic_category);

    companion object {
        fun fromName(name: String): CategoryIcon {
            return values().find { it.name.equals(name, ignoreCase = true) } ?: DEFAULT
        }
    }
}

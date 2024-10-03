package com.example.eco_life.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class EcoPlace(
    @DrawableRes val imageResId: Int,
    @StringRes val nameResId: Int,
    @StringRes val addressResId: Int,
    @StringRes val workingDaysResId: Int,
    @StringRes val workingHoursResId: Int,
)

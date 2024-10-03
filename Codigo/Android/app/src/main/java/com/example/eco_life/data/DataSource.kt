package com.example.ecoshops.data

import com.example.eco_life.R
import com.example.eco_life.model.EcoPlace

class DataSource {
    fun loadEcoPlaces(): List<EcoPlace> {
        return listOf<EcoPlace>(
            EcoPlace(R.drawable.shop1, R.string.place1, R.string.address1, R.string.days1, R.string.hours1),
            EcoPlace(R.drawable.shop2, R.string.place2, R.string.address2, R.string.days2, R.string.hours2),
            EcoPlace(R.drawable.shop3, R.string.place3, R.string.address3, R.string.days3, R.string.hours3),
            EcoPlace(R.drawable.shop4, R.string.place4, R.string.address4, R.string.days4, R.string.hours4),
            EcoPlace(R.drawable.shop5, R.string.place5, R.string.address5, R.string.days5, R.string.hours5),
            EcoPlace(R.drawable.shop6, R.string.place6, R.string.address6, R.string.days6, R.string.hours6),
            EcoPlace(R.drawable.shop7, R.string.place7, R.string.address7, R.string.days7, R.string.hours7),
            EcoPlace(R.drawable.shop8, R.string.place8, R.string.address8, R.string.days8, R.string.hours8),
            EcoPlace(R.drawable.shop9, R.string.place9, R.string.address9, R.string.days9, R.string.hours9),
            EcoPlace(R.drawable.shop10, R.string.place10, R.string.address10, R.string.days10, R.string.hours10)
        )
    }
}
package com.example.mynutrition.domain.model.enums

enum class ActivityLevel(val factor: Double) {
    SEDENTARY(1.2),
    LIGHT(1.375),
    MODERATE(1.55),
    HIGH(1.725),
    EXTREME(1.9)
}
package com.aroniez.futaa.models

import androidx.room.Entity
import androidx.annotation.NonNull

@Entity(primaryKeys = ["id"])
data class Favorite(
        @NonNull var id: Long
)
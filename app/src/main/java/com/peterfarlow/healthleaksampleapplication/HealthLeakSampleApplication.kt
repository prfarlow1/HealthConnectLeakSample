package com.peterfarlow.healthleaksampleapplication

import android.app.Application
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.NutritionRecord
import androidx.health.connect.client.records.WeightRecord

class HealthLeakSampleApplication : Application() {

    companion object {
        val recordType = setOf(
            ExerciseSessionRecord::class,
            HeightRecord::class,
            NutritionRecord::class,
            WeightRecord::class,
        )

        val permissions = recordType.map {
            HealthPermission.getReadPermission(
                it
            )
        }.toSet()
    }
}


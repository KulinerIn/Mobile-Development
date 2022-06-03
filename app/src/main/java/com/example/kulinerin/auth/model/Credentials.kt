package com.example.kulinerin.auth.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credentials (
    val email: String,
    val password: String
) : Parcelable
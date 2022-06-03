package com.example.kulinerin.auth.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserAccount (
    val email: String,
    val firstName: String,
    val lastName: String
) : Parcelable
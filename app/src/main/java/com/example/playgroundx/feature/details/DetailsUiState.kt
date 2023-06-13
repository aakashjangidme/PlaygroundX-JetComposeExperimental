package com.example.playgroundx.feature.details

import com.example.playgroundx.domain.model.Details
import com.example.playgroundx.core.util.formatDate

data class DetailsUiState(

    val isLoading: Boolean = false, val detail: Details = Details(), val error: String = ""

) {
    val formattedUserSince = formatDate(detail.userSince)
}
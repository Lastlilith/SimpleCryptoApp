package com.example.simplecryptoapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameContainerDto(
    @SerializedName("CoinInfo")
    @Expose
    val coinNameDto: CoinNameDto? = null
)
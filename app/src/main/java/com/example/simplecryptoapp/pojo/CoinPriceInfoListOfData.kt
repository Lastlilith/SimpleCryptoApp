package com.example.simplecryptoapp.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class CoinPriceInfoListOfData (
    @SerializedName("Data")
    @Expose
    val data: List<Datum>? = null
)
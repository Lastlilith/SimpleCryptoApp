package com.example.simplecryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecryptoapp.data.repository.CoinRepositoryImpl
import com.example.simplecryptoapp.domain.GetCoinInfoListUseCase
import com.example.simplecryptoapp.domain.GetCoinInfoUseCase
import com.example.simplecryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }


}
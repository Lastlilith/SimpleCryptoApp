package com.example.simplecryptoapp.domain

class GetCoinInfoListUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke() = repository.getCoinInfoList()
}
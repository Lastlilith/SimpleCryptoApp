package com.example.simplecryptoapp.domain

class LoadDataUseCase(
    private val repository: CoinRepository
) {

   operator fun invoke() = repository.loadData()
}
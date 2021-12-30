package com.example.simplecryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.simplecryptoapp.api.ApiFactory
import com.example.simplecryptoapp.database.AppDatabase
import com.example.simplecryptoapp.pojo.CoinPriceInfo
import com.example.simplecryptoapp.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val priceList = db.coinPriceInfoDao().getPriceList()

//    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
//        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
//    }

    init {
        loadData()
    }

    private fun loadData() {
        val disposable: Disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString { "," } as String }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success: $it")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jSonObject = coinPriceInfoRawData.coinPriceInfoJsonObject?: return result
        val coinKeySet = jSonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jSonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
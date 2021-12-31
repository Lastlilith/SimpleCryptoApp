package com.example.simplecryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecryptoapp.adapters.CoinInfoAdapter
import com.example.simplecryptoapp.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var rvCoinPriceList: RecyclerView

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        rvCoinPriceList = findViewById(R.id.rvCoinPriceList)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickedListener = object: CoinInfoAdapter.OnCoinClickedListener {
            override fun onCoinCLick(coinPriceInfo: CoinPriceInfo) {
                Log.d("On_Click", coinPriceInfo.fromSymbol)
            }

        }

        rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })

    }
}
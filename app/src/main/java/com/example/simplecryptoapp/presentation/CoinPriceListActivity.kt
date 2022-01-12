package com.example.simplecryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecryptoapp.R
import com.example.simplecryptoapp.domain.CoinInfo
import com.example.simplecryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var rvCoinPriceList: RecyclerView

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        rvCoinPriceList = findViewById(R.id.rvCoinPriceList)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickedListener = object : CoinInfoAdapter.OnCoinClickedListener {
            override fun onCoinCLick(coinPriceInfo: CoinInfo) {
                val intent = CoinDetailsActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }
        }

        rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }
    }
}
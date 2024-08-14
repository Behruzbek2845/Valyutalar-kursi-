package com.behruzbek0430.valyutakursi

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.behruzbek0430.valyutakursi.Api.ApiClient
import com.behruzbek0430.valyutakursi.adapters.RvAdapter
import com.behruzbek0430.valyutakursi.adapters.SpinnerAdapter
import com.behruzbek0430.valyutakursi.databinding.ActivityMainBinding
import com.behruzbek0430.valyutakursi.databinding.ItemConvertBinding
import com.behruzbek0430.valyutakursi.models.MyMoney
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var listNomi: ArrayList<String>
    private lateinit var rvAdapter: RvAdapter
    private lateinit var list: ArrayList<MyMoney>
    lateinit var spinnerAdapter: SpinnerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

    override fun onResume() {
        super.onResume()
        var qiymat1 = 0f
        var qiymat2 = 0f
        var nomi1 = ""
        var nomi2 = ""
        ApiClient.getApiService().getNotes()
            .enqueue(object : Callback<List<MyMoney>> {
                override fun onResponse(p0: Call<List<MyMoney>>, p1: Response<List<MyMoney>>) {
                    if (p1.isSuccessful) {
                        list = p1.body() as ArrayList<MyMoney>
                        list.add(
                            MyMoney(
                                "SO'M",
                                "Uzbek sum",
                                "UZB sum",
                                "O'zbek so'mi",
                                "uzb sum",
                                "10221",
                                "01.08.2024",
                                "0",
                                "1",
                                "1",
                                1
                            )
                        )
                        rvAdapter = RvAdapter(list)
                        binding.rv.adapter = rvAdapter
                        Log.d(TAG, "onResponse: ${p1.body()}")
                        listNomi = ArrayList()
                        for (valyuta in list) {
                            listNomi.add(valyuta.Ccy)

                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(p0: Call<List<MyMoney>>, p1: Throwable) {
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("Sizda internet tarmog'i mavjud emas!!!")
                    dialog.setPositiveButton("Ok") { _, _ ->
                        finish()
                    }
                    dialog.setNegativeButton("Yoqish") { _, _ ->
                        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                        startActivity(intent)
                    }
                    dialog.show()
                }
            })


        binding.cal.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            val customAlertDialogBinding = ItemConvertBinding.inflate(layoutInflater)
            builder.setView(customAlertDialogBinding.root)

            spinnerAdapter = SpinnerAdapter(listNomi)
            customAlertDialogBinding.spin1.adapter = spinnerAdapter
            customAlertDialogBinding.spin2.adapter = spinnerAdapter

            customAlertDialogBinding.spin1.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        qiymat1 = list[position].Rate.toFloat()
                        nomi1 = list[position].Ccy
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            customAlertDialogBinding.spin2.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        qiymat2 = list[position].Rate.toFloat()
                        nomi2 = list[position].Ccy
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            customAlertDialogBinding.btbConvert.setOnClickListener {
                if (customAlertDialogBinding.edtSum.text.isNotBlank()) {
                    val sum = customAlertDialogBinding.edtSum.text.toString().toFloat()
                    val c = qiymat1 / qiymat2
                    val result = c * sum
                    customAlertDialogBinding.txtSum.text = "${"%.3f".format(result)} ${nomi2}"
                } else {
                    Toast.makeText(this, "Summaga miqdor kiriting", Toast.LENGTH_SHORT).show()
                }


            }


            val alertDialog = builder.create()
            alertDialog.show()


//
//        binding.btbConvert.setOnClickListener {
//            if (binding.edtSum.text.isNotBlank()){
//                val sum = binding.edtSum.text.toString().toFloat()
//                val c = qiymat1/qiymat2
//                val result = c*sum
//                binding.txtSum.text = "%.3f".format(result)
//            } else {
//                Toast.makeText(this, "Summaga miqdor kiriting", Toast.LENGTH_SHORT).show()
//            }
//
//
//        }


        }
    }
}
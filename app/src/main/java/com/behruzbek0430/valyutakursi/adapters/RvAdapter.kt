package com.behruzbek0430.valyutakursi.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.behruzbek0430.valyutakursi.R
import com.behruzbek0430.valyutakursi.databinding.ItemRvBinding
import com.behruzbek0430.valyutakursi.models.MyMoney

class RvAdapter(val list: ArrayList<MyMoney>): RecyclerView.Adapter<RvAdapter.VH>() {
    inner class VH(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        @SuppressLint("SetTextI18n")
        fun OnBind(myMoney: MyMoney) {
            itemRvBinding.txtDate.text = myMoney.Date
            itemRvBinding.diff.text = myMoney.Diff
            if (myMoney.Diff.toFloat() > 0){
                itemRvBinding.diff.setTextColor(Color.GREEN)
                itemRvBinding.imgDiff.setImageResource(R.drawable.baseline_arrow_upward_24)
            } else if (myMoney.Diff.toFloat() < 0) {
                itemRvBinding.diff.setTextColor(Color.RED)
                itemRvBinding.imgDiff.setImageResource(R.drawable.baseline_arrow_downward_24)
            } else if (myMoney.Diff.toFloat() == 0f){
                itemRvBinding.diff.setTextColor(Color.WHITE)
                itemRvBinding.imgDiff.setImageResource(R.drawable.baseline_horizontal_rule_24)
            }
            itemRvBinding.txtSum.text = "${myMoney.Rate} so'm"
            itemRvBinding.nameCcy.text = "${myMoney.CcyNm_UZ} (${myMoney.Ccy})"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.OnBind(list[position])
    }

}
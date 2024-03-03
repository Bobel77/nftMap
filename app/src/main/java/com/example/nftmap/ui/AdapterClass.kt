package com.example.nftmap.ui

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nftmap.R
import com.example.nftmap.nftwallet.NftsOwned
import kotlinx.coroutines.runBlocking
import java.net.URL

class AdapterClass (
    private val dataList: ArrayList<NftsOwned>
): RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.itemnft_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]


        holder.rvImage.apply {
            Glide.with(this)
                .load(currentItem.source)
                .centerCrop()
                .placeholder(R.drawable.ic_home_black_24dp)
                .into(this);
               /* this.setImageResource(R.drawable.ic_launcher_background)
*/
        }
        holder.rvTitel.text= currentItem.name
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderClass(itemView: View):RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.nftImage)
        val rvTitel: TextView = itemView.findViewById(R.id.txtNftName)
    }

}
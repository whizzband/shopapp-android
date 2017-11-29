package com.client.shop.ui.cart.adapter

import android.content.Context
import android.view.View
import com.client.shop.ui.base.ui.recycler.OnItemClickListener
import com.client.shop.ui.base.ui.recycler.adapter.BaseRecyclerAdapter
import com.client.shop.ui.item.cart.CartItem
import com.client.shop.ui.item.cart.FooterCartItem
import com.domain.entity.CartProduct
import com.domain.formatter.NumberFormatter
import kotlinx.android.synthetic.main.item_footer_cart.view.*

class CartAdapter(dataList: List<CartProduct>, onItemClickListener: OnItemClickListener) :
        BaseRecyclerAdapter<CartProduct>(dataList, onItemClickListener) {

    var actionListener: CartItem.ActionListener? = null
    private val formatter = NumberFormatter()

    init {
        withFooter = true
    }

    override fun bindData(itemView: View, data: CartProduct, position: Int) {
        if (itemView is CartItem) {
            itemView.setCartProduct(data)
            itemView.actionListener = actionListener
        }
    }

    override fun bindFooterData(itemView: View, position: Int) {
        if (itemView is FooterCartItem) {
            itemView.setData(dataList)
        }
    }

    override fun getItemView(context: Context, viewType: Int): View {
        return CartItem(context, formatter)
    }

    override fun getFooterView(context: Context): View? {
        val footerView = FooterCartItem(context, formatter)
        footerView.checkoutButton.setOnClickListener {
            onItemClickListener.onFooterClicked()
        }
        return footerView
    }

    override fun getItemId(position: Int): Long {
        return if (dataList.size > position) {
            dataList[position].productVariant.id.hashCode().toLong()
        } else {
            -1
        }
    }
}
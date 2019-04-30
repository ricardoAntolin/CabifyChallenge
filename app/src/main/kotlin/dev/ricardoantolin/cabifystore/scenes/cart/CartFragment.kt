package dev.ricardoantolin.cabifystore.scenes.cart

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseFragment
import dev.ricardoantolin.cabifystore.domain.models.TSHIRT_DISCOUNT_CODE
import dev.ricardoantolin.cabifystore.domain.models.VOUCHER_DISCOUNT_CODE
import dev.ricardoantolin.cabifystore.extensions.inflate
import dev.ricardoantolin.cabifystore.extensions.setupSnackbar
import dev.ricardoantolin.cabifystore.models.UIDiscount
import dev.ricardoantolin.cabifystore.models.UIProduct
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.cart_price_resume.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.progress_overlay.*
import org.jetbrains.anko.sdk23.listeners.onClick
import org.jetbrains.anko.support.v4.alert

class CartFragment : BaseFragment() {
    override lateinit var progressBarOverlay: LinearLayout
    private val onAddOneCartButtonClick = PublishSubject.create<UIProduct>()
    private val onRemoveOneCartButtonClick = PublishSubject.create<UIProduct>()
    private val onRemoveAllCartButtonClick = PublishSubject.create<UIProduct>()
    private val adapter = CartAdapter(
        listOf(),
        this::removeAllTypeProduct,
        this::addOneProduct,
        this::removeOneProduct
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_cart)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBarOverlay = progressbarOverlay
        bindViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpRecycler()
        setListeners()
    }

    @SuppressLint("PrivateResource")
    private fun setUpToolbar() {
        (activity as? AppCompatActivity)?.let {
            it.setSupportActionBar(appBar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(it, R.drawable.close_menu))
        }
    }

    private fun setUpRecycler() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        val topPadding = resources.getDimensionPixelSize(R.dimen.cart_item_padding_decoration)
        recyclerView.addItemDecoration(CartItemDecoration(topPadding))
    }

    private fun setListeners(){
        btnBuyNow.onClick { alert(R.string.proceed_checkout).show() }
    }

    private fun bindViewModel() {
        val viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(CartViewModel::class.java)

        val output = viewModel.transform(
            CartViewModel.Input(
                Observable.just(Unit),
                onRemoveOneCartButtonClick,
                onRemoveAllCartButtonClick,
                onAddOneCartButtonClick
            )
        )

        output.cartObserver.observe(this, Observer {
            adapter.setItems(it.products)
            bindDiscounts(it.discounts)
            bindTotalPrice(it.total)
            btnBuyNow.isActivated = it.products.isNotEmpty()
        })

        output.removeOneProductObserver.observe(this, Observer {})
        output.removeAllProductObserver.observe(this, Observer {})
        output.addProductObserver.observe(this, Observer {})

        output.activityTracker.observe(this, Observer {
            trackBackgroudProgress(it)
        })

        content.setupSnackbar(this, output.errorTracker, Snackbar.LENGTH_LONG)
    }

    private fun bindDiscounts(discounts: List<UIDiscount>) {
        bindVoucherDiscounts(discounts.firstOrNull { it.code == VOUCHER_DISCOUNT_CODE })
        bindTshirtDiscounts(discounts.firstOrNull { it.code == TSHIRT_DISCOUNT_CODE })
    }

    @SuppressLint("SetTextI18n")
    private fun bindVoucherDiscounts(discount: UIDiscount?) {
        twoOneDiscountContainer.visibility = discount?.let {
            txtTwoOneDiscountQuantity.text = "${discount.quantity} €"
            View.VISIBLE
        } ?: View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun bindTshirtDiscounts(discount: UIDiscount?) {
        bulkDiscountContainer.visibility = discount?.let {
            txtBulkDiscountQuantity.text = "${discount.quantity} €"
            View.VISIBLE
        } ?: View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun bindTotalPrice(total: Float){
        txtTotalQuantity.text = "$total €"
    }

    private fun addOneProduct(product: UIProduct) {
        onAddOneCartButtonClick.onNext(product)
    }

    private fun removeOneProduct(product: UIProduct) {
        onRemoveOneCartButtonClick.onNext(product)
    }

    private fun removeAllTypeProduct(product: UIProduct) {
        onRemoveAllCartButtonClick.onNext(product)
    }
}

class CartItemDecoration(
    private val topPadding: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.top = topPadding
    }
}
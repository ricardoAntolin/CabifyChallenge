package dev.ricardoantolin.cabifystore.common

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.ricardoantolin.cabifystore.di.Injectable
import java.math.BigInteger
import java.security.SecureRandom
import javax.inject.Inject

abstract class BaseFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract var progressBarOverlay: LinearLayout

    var fragmentId: String? = null

    init {
        generateId()
    }

    open fun generateId() {
        var hash = ""
        this.javaClass.simpleName.map { hash = hash + BigInteger(1, SecureRandom()).toString() + it.toInt() + it }
        fragmentId = hash
    }

    protected fun trackBackgroudProgress(isRunning: Boolean){
        if (isRunning){
            showLoading()
        }else {
            hideLoading()
        }
    }

    open fun showLoading() {
        progressBarOverlay.visibility = View.VISIBLE
    }

    open fun hideLoading(){
        progressBarOverlay.visibility =  View.GONE
    }
}
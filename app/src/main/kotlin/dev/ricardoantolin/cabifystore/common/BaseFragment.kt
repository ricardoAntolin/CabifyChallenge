package dev.ricardoantolin.cabifystore.common

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import dev.ricardoantolin.cabifystore.di.Injectable
import java.math.BigInteger
import java.security.SecureRandom

abstract class BaseFragment: Fragment(), Injectable {
    abstract var progressBar: ProgressBar

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
        progressBar.isIndeterminate = true
        progressBar.visibility = View.VISIBLE
    }

    open fun hideLoading(){
        progressBar.visibility =  View.GONE
    }
}
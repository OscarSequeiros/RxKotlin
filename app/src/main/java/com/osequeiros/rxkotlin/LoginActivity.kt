package com.osequeiros.rxkotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.example.osequeiros.universe.util.Patterns
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /** Email validation: */
        val userObservable = RxTextView.textChanges(edit_login_user)
                .map { t -> Patterns.emailPattern.matcher(t).matches() }

        userObservable.distinctUntilChanged()
                .map { b -> if (b) R.drawable.ic_user else R.drawable.ic_user_alert }
                .subscribe { id -> edit_login_user.
                        setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0) }

        /** Password validation */
        val passObservable = RxTextView.textChanges(edit_login_password)
                .map { t -> t.length > 5 }

        passObservable.distinctUntilChanged()
                .map { b -> if (b) R.drawable.ic_lock_open else R.drawable.ic_lock }
                .subscribe { id -> edit_login_password.
                        setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0) }

        /** Sign In observer */
        val signInEnabled: Observable<Boolean> = Observable.combineLatest(
                userObservable, passObservable, BiFunction { u, p -> u && p })

        signInEnabled.distinctUntilChanged()
                .subscribe { enabled -> btn_login_signin.isEnabled = enabled }

        signInEnabled.distinctUntilChanged()
                .map { b -> if (b) R.color.colorAccent else R.color.colorTextDisabled }
                .subscribe { color -> btn_login_signin.backgroundTintList =
                        ContextCompat.getColorStateList(this, color) }

        /** Click over Sign In */
        btn_login_signin.setOnClickListener {
            Snackbar.make(layout_login, "Doing Login...", Snackbar.LENGTH_SHORT).show()
        }
    }
}

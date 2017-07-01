package com.example.osequeiros.universe.util

import java.util.regex.Pattern

/**
 * Created by osequeiros on 11/06/17.
 * Patters class
 */

class Patterns {
    companion object {
        val emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    }
}

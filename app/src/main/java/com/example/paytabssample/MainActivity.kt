package com.example.paytabssample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.payment.paymentsdk.PaymentSdkActivity.Companion.startCardPayment
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.*
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface

class MainActivity : AppCompatActivity(), CallbackPaymentInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        payNow()
    }

    fun payNow(){
        val profileId = "47554"
        val serverKey = "SRJNLLK22J-JB6KG2G6K9-DWLH2WNLGT"
        val clientLey = "CBKMDM-RDM962-GKTMTG-Q27HV9"
        val locale = PaymentSdkLanguageCode.EN
        val screenTitle = "Test SDK"
        val cartId = "123456"
        val cartDesc = "cart description"
        val currency = "USD"
        val amount = 20.0

        val tokeniseType = PaymentSdkTokenise.NONE // tokenise is off

        val transType = PaymentSdkTransactionType.SALE;


        val tokenFormat =  PaymentSdkTokenFormat.Hex32Format()
        val billingData = PaymentSdkBillingDetails(
            "City",
            "AE",
            "email1@domain.com",
            "name ",
            "phone", "state",
            "address street", "zip"
        )

        val shippingData = PaymentSdkShippingDetails(
            "City",
            "AE",
            "email1@domain.com",
            "name ",
            "phone", "state",
            "address street", "zip"
        )
        val configData = PaymentSdkConfigBuilder(profileId, serverKey, clientLey, amount ?: 0.0, currency)
            .setCartDescription(cartDesc)
            .setLanguageCode(locale)
            .setBillingData(billingData)
            .setMerchantCountryCode("AE") // ISO alpha 2
            .setShippingData(shippingData)
            .setCartId(cartId)
            .setTransactionType(transType)
            .showBillingInfo(false)
            .showShippingInfo(true)
            .forceShippingInfo(true)
            .setScreenTitle(screenTitle)
            .build()
        startCardPayment(this, configData, callback=this)
    }

    override fun onError(error: PaymentSdkError) {
        Toast.makeText(this, error.msg, Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentCancel() {
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {

        Toast.makeText(
            this,
            "Payment Success: ${paymentSdkTransactionDetails.isSuccess}",
            Toast.LENGTH_SHORT
        ).show()
    }
}
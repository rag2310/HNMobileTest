package com.rago.hnmobiletest.ui.articlesdetails

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.rago.hnmobiletest.MainActivity
import com.rago.hnmobiletest.R
import com.rago.hnmobiletest.databinding.FragmentArticlesDetailsBinding

class ArticlesDetailsFragment : Fragment() {

    private val articlesDetailsFragmentArgs by navArgs<ArticlesDetailsFragmentArgs>()

    private lateinit var binding: FragmentArticlesDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        println("articlesDetailsFragmentArgs.url ${articlesDetailsFragmentArgs.url}")
        binding = FragmentArticlesDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackButton.setOnClickListener {
            it.findNavController().popBackStack()
        }
//        binding.wvArticles.settings.javaScriptEnabled = true

        binding.wvArticles.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                showSSLErrorDialog(handler)
            }
        }

        binding.wvArticles.loadUrl("https://www.reuters.com/world/europe/google-apple-remove-navalny-app-stores-russian-elections-begin-2021-09-17/")

    }

    private fun showSSLErrorDialog(handler: SslErrorHandler?) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("HNMobileTest")
        builder.setMessage("Did something bad happen?")
        builder.setPositiveButton("YES") { dialog, which ->
            handler?.proceed()
        }
        builder.setNegativeButton("No") { dialog, which ->
            handler?.cancel()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}
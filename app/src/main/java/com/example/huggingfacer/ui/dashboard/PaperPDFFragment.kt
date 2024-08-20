package com.example.huggingfacer.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.huggingfacer.R
import com.github.barteksc.pdfviewer.PDFView
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class PaperPDFFragment : Fragment() {

    private lateinit var pdfView: PDFView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paper_pdf, container, false)

        pdfView = view.findViewById(R.id.pdfView)

        val pdfLink = arguments?.getString("pdfLink")
        if (pdfLink != null) {
            downloadAndShowPdf(pdfLink)
        }

        return view
    }

    private fun downloadAndShowPdf(pdfUrl: String) {
        thread {
            try {
                val url = URL(pdfUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connect()

                val file = File(requireContext().cacheDir, "temp.pdf")
                val fos = FileOutputStream(file)
                val inputStream = connection.inputStream

                val buffer = ByteArray(1024)
                var len: Int
                while (inputStream.read(buffer).also { len = it } > 0) {
                    fos.write(buffer, 0, len)
                }

                fos.close()
                inputStream.close()

                requireActivity().runOnUiThread {
                    pdfView.fromFile(file)
                        .load()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

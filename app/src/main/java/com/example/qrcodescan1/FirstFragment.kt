    package com.example.qrcodescan1

    import android.content.pm.PackageManager
    import android.os.Bundle
    import android.util.Log

    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.appcompat.app.AlertDialog
    import androidx.core.app.ActivityCompat
    import androidx.core.content.ContextCompat
    import androidx.navigation.fragment.findNavController
    import com.example.qrcodescan1.databinding.FragmentFirstBinding
    import me.dm7.barcodescanner.zxing.ZXingScannerView
    import java.io.BufferedReader
    import java.io.InputStreamReader
    import java.io.OutputStreamWriter
    import java.net.Socket
    import kotlin.Result as Result1


    class FirstFragment : Fragment() {

        private var _binding: FragmentFirstBinding? = null
        private val binding get() = _binding!!
        private lateinit var scannerView: ZXingScannerView
        val CAMERA_PERMISSION_REQUEST_CODE = 1001



        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {

            _binding = FragmentFirstBinding.inflate(inflater, container, false)
            val view = binding.root
            scannerView = view.findViewById(R.id.scanner_view)
                val cameraPermission = android.Manifest.permission.CAMERA
                val isCameraPermissionGranted = ContextCompat.checkSelfPermission(requireContext(), cameraPermission) == PackageManager.PERMISSION_GRANTED
                if (!isCameraPermissionGranted) {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(cameraPermission), CAMERA_PERMISSION_REQUEST_CODE)
                } else {
                    scannerView.startCamera()
                    scannerView.setResultHandler { result ->



                            binding.textView2.setText(result.text)
                            val result = result.text
                            ServerConnectionHelper.sendResultToServer(result)
                            onResume()


                    }
                }
            return view
        }


        override fun onResume() {
            super.onResume()
            scannerView.startCamera() // Khởi động máy ảnh khi Fragment resumed
        }

        override fun onPause() {
            super.onPause()
            scannerView.stopCamera() // Dừng máy ảnh khi Fragment paused
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
package com.example.qrcodescan1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.qrcodescan1.databinding.FragmentSecondBinding
import java.io.IOException
import java.net.InetAddress
import java.net.Socket


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        if(!Ip_Port.getIp().toString().equals("0")){
            binding.button.isEnabled=false;
        }

        binding.button.setOnClickListener {
            val ipInput = binding.txtIp.text.toString()
            val portInput = binding.txtpport.text.toString()

            if (ipInput.isNotEmpty() && portInput.isNotEmpty()) {
                val serverIp = ipInput
                val serverPort = portInput.toInt()

                Thread {
                    try {
                        val addr: InetAddress = InetAddress.getByName(serverIp)
                        val socket = Socket(addr, serverPort)
                        Ip_Port.setIp(serverIp)
                        Ip_Port.setPort(portInput)


                        // Xử lý khi kết nối thành công
                        activity?.runOnUiThread {
                            showMessageDialog("Connection Status", "Connected to the server")
                        }

                        // Đóng kết nối khi đã sử dụng xong
                        socket.close()
                    } catch (e: IOException) {
                        // Xử lý khi có lỗi kết nối
                        activity?.runOnUiThread {
                            showMessageDialog("Connection Error", "Cannot connect to the server:(Kiểm tra mạng hoặc wifi) " + e.message)
                        }
                    } catch (e: Exception) {
                        // Xử lý ngoại lệ chung
                        val errorMessage = "An error occurred: " + e.message
                        activity?.runOnUiThread {
                            showMessageDialog("Error", errorMessage)
                        }
                        Log.e("ExceptionLog", "An exception occurred", e)
                    }
                }.start()
            } else {
                showMessageDialog("Barcode Scanned", "Nhập đầy đủ")
            }

        }


    }

    private fun showMessageDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
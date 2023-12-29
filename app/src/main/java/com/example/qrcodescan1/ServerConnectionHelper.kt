package com.example.qrcodescan1

import androidx.annotation.IntegerRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.qrcodescan1.databinding.FragmentSecondBinding
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.InetAddress
import java.net.Socket

class ServerConnectionHelper {
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    companion object {
        fun sendResultToServer(result: String) {
            // Thực hiện kết nối và gửi dữ liệu đến máy chủ trong một luồng mới


                Thread {
                try {
                    val serverIp = Ip_Port.getIp() // Địa chỉ IP của máy chủ
                    val serverPort = Integer.parseInt(Ip_Port.getPort()) // Cổng mà máy chủ đang lắng nghe

                    val addr: InetAddress = InetAddress.getByName(serverIp)
                    val socket = Socket(addr, serverPort)

                    // Gửi dữ liệu đến máy chủ
                    val writer = OutputStreamWriter(socket.getOutputStream())
                    writer.write(result)
                    writer.flush()

                    // Đóng kết nối khi đã sử dụng xong
                    socket.close()
                } catch (e: IOException) {
                    // Xử lý khi có lỗi kết nối
                    println("Cannot connect to the server: ${e.message}")
                }
            }.start()
            }


    }
}
package com.knokode.trader.cyrpto.bitfinex

import com.google.gson.Gson
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI


fun main(args: Array<String>) {
    BitFinexClient().connect()

}

class BitFinexClient : WebSocketClient(URI("wss://api.bitfinex.com/ws")) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        connection.send(Gson().toJson(BitfinexRequest("subscribe", "trades", "ETCBTC")))
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMessage(message: String?) {
        println(message)
    }

    override fun onError(ex: Exception?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


data class BitfinexRequest(val event: String, val channel: String? = null, val pair: String? = null)

/*
 * Copyright 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidthings.loopback;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.UartDevice;
import com.google.android.things.pio.UartDeviceCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Example activity that provides a UART loopback on the
 * specified device. All data received at the specified
 * baud rate will be transferred back out the same UART.
 */
public class LoopbackActivity extends Activity {
    private static final String TAG = "LoopbackActivity";

    // UART Configuration Parameters
    private static final int BAUD_RATE = 9600;
    private static final int DATA_BITS = 8;
    private static final int STOP_BITS = 1;

    private static final int CHUNK_SIZE = 16;  // size of buffer required

    private PeripheralManagerService mService = new PeripheralManagerService();

    private HandlerThread mInputThread;
    private Handler mInputHandler;

    private UartDevice mLoopbackDevice;

    private Runnable mTransferUartRunnable = new Runnable() {
        @Override
        public void run() {
            transferUartData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Loopback Created");

        // Create a background looper thread for I/O
        mInputThread = new HandlerThread("InputThread");
        mInputThread.start();
        mInputHandler = new Handler(mInputThread.getLooper());

        // Attempt to access the UART device
        try {
            openUart(BoardDefaults.getUartName(), BAUD_RATE);
            // Read any initially buffered data
            mInputHandler.post(mTransferUartRunnable);
        } catch (IOException e) {
            Log.e(TAG, "Unable to open UART device", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Loopback Destroyed");

        // Terminate the worker thread
        if (mInputThread != null) {
            mInputThread.quitSafely();
        }

        // Attempt to close the UART device
        try {
            closeUart();
        } catch (IOException e) {
            Log.e(TAG, "Error closing UART device:", e);
        }
    }

    /**
     * Callback invoked when UART receives new incoming data.
     */
    private UartDeviceCallback mCallback = new UartDeviceCallback() {
        @Override
        public boolean onUartDeviceDataAvailable(UartDevice uart) {
            // Queue up a data transfer
            transferUartData();
            //Continue listening for more interrupts
            return true;
        }

        @Override
        public void onUartDeviceError(UartDevice uart, int error) {
            Log.w(TAG, uart + ": Error event " + error);
        }
    };

    /* Private Helper Methods */

    /**
     * Access and configure the requested UART device for 8N1.
     *
     * @param name Name of the UART peripheral device to open.
     * @param baudRate Data transfer rate. Should be a standard UART baud,
     *                 such as 9600, 19200, 38400, 57600, 115200, etc.
     *
     * @throws IOException if an error occurs opening the UART port.
     */
    private void openUart(String name, int baudRate) throws IOException {
        mLoopbackDevice = mService.openUartDevice(name);
        // Configure the UART
        mLoopbackDevice.setBaudrate(baudRate);
        mLoopbackDevice.setDataSize(DATA_BITS);
        mLoopbackDevice.setParity(UartDevice.PARITY_NONE);
        mLoopbackDevice.setStopBits(STOP_BITS);

        mLoopbackDevice.registerUartDeviceCallback(mCallback, mInputHandler);
    }

    /**
     * Close the UART device connection, if it exists
     */
    private void closeUart() throws IOException {
        if (mLoopbackDevice != null) {
            mLoopbackDevice.unregisterUartDeviceCallback(mCallback);
            try {
                mLoopbackDevice.close();
            } finally {
                mLoopbackDevice = null;
            }
        }
    }

    /**
     * Loop over the contents of the UART RX buffer, transferring each
     * one back to the TX buffer to create a loopback service.
     *
     * Potentially long-running operation. Call from a worker thread.
     */
    private void transferUartData() {
        // Loop until there is no more data in the RX buffer.
        if (mLoopbackDevice != null) try {
            byte[] buffer = new byte[CHUNK_SIZE];
            //String buf= "";

            //m,byte[] buffer1 = buf.getBytes("UTF-8");
            //byte[] buffer = Input.getBytes("UTF-8");
            int read;
            String Input = "P"; // Reply to be sent
          //  read = mLoopbackDevice.read(buffer, buffer.length);
            //String s = new String(buffer, "US-ASCII");
            //Log.v(TAG,re; );
           //Log.d(TAG, buffer + " ");
            //Log.d(TAG,mLoopbackDevice.write(buffer, buffer.length) + " ");
            byte[] b = Input.getBytes("UTF-8");  // string to bytes
            while ((read = mLoopbackDevice.read(buffer, buffer.length)) > 0) { // check if data is coming from serial
               // Log.d(TAG, "R ");
                String s = new String(buffer);  // reading data from serial and storing in buffer
                String [] a = s.split (" ");    // splitting incoming data into array

                Log.d(TAG, a[0] );               // printing in android monitor
                if ( a[0].equals("hey") )        // equality check to find proper data is coming or not
                {
                    mLoopbackDevice.write(b, b.length);  // writing reply in serial
                    Log.d(TAG, new String(b) );          // writing reply in android monitor
                }

            }

        } catch (IOException e) {
            Log.w(TAG, "Unable to transfer data over UART", e);
        }
    }
}

/*   string to byte
    String s = "some text here";
    byte[] b = s.getBytes("UTF-8");    // to get string as array of byte's

byte to string
byte[] b = {(byte) 99, (byte)97, (byte)116};
String s = new String(b, "US-ASCII");
*/
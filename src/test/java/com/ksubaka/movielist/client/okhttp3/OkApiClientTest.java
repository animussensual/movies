package com.ksubaka.movielist.client.okhttp3;

import com.ksubaka.movielist.client.ApiClient;
import com.ksubaka.movielist.client.ApiClientConfig;
import com.ksubaka.movielist.client.ApiResponse;
import com.ksubaka.movielist.client.GET;
import com.ksubaka.movielist.client.Status;
import org.junit.Test;

import javax.net.ServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class OkApiClientTest {

    @Test
    public void connectionFailureGivesFailureResponse() throws IOException {
        ApiClientConfig config = ApiClientConfig.builder()
                .withConnectionTimeout(100)
                .withSocketTimeout(100)
                .build();

        ApiClient client = new OkApiClient(config);

        //Expects that nobody listens on port 12345
        ApiResponse response = client.sendRequest(GET.of("http://localhost:12345"));

        assertThat(response.getStatus(), is(Status.FAILURE));
        assertThat(response.getErrorCode(), is(Optional.of(500)));
        assertThat(response.getErrorMessage(), is(Optional.of("Get request failed")));
    }

    @Test
    public void successfulRequestGivesSuccessResponse() throws IOException, InterruptedException {
        CountDownLatch socketStarted = createServerSocket();
        ApiClient client = new OkApiClient();

        //Expects that nobody listens on port 12345

        socketStarted.await();
        ApiResponse response = client.sendRequest(GET.<String>of("http://127.0.0.1:12345"));

        assertThat(response.getStatus(), is(Status.SUCCESS));
        Optional<byte[]> body = response.getBody();
        assertTrue(body.isPresent());
        assertThat(new String(body.get(), "UTF8"), is("B"));
    }

    private CountDownLatch createServerSocket() {
        CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture.runAsync(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = ServerSocketFactory.getDefault().createServerSocket(12345);
                latch.countDown();
                try (Socket socket = serverSocket.accept()) {
                    BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    is.readLine();
                    socket.getOutputStream().write("HTTP/1.1 200 OK\n".getBytes());
                    socket.getOutputStream().write("Content-Length: 1\n".getBytes());
                    socket.getOutputStream().write("Content-Type: text/html\n\n".getBytes());
                    socket.getOutputStream().write("B\n\n".getBytes());
                    socket.getOutputStream().flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return latch;
    }
}

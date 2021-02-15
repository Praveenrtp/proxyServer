import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;

public class ProxyServerHandler implements Handler<HttpServerRequest> {
  Vertx vertx;


  public ProxyServerHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void handle(HttpServerRequest httpServerRequest) {
    String path = httpServerRequest.getParam("path");
    if (path == null || path.isEmpty()) {
      httpServerRequest.response().setStatusCode(400).end("Bad Request because of empty path");
    } else {
      path = "http://" + path;
    }
    vertx.createHttpClient()
        .getNow(path, response -> {
          response.handler(responseBody -> {
            httpServerRequest.response().end(responseBody);
          });
        });
  }
}

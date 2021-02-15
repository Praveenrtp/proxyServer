import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class ProxyServer extends AbstractVerticle {
  @Override
  public void start(Future<Void> future) {
    vertx.createHttpServer()
        .requestHandler(new ProxyServerHandler(vertx))
        .listen(25,
            result -> {
              if (result.succeeded()) {
                future.complete();
              }
              if (result.failed()) {
                future.cause();
              }
            });
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ProxyServer());
  }
}

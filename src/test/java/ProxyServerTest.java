import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ProxyServerTest {
  Vertx vertx;

  @Before
  public void setup(TestContext testContext) {
    vertx = Vertx.vertx();

    vertx.deployVerticle(ProxyServer.class.getName(),
        testContext.asyncAssertSuccess());
  }

  @Test
  public void whenReceivedResponse_thenSuccess(TestContext testContext) {
    Async async = testContext.async();

    vertx.createHttpClient()
        .getNow(9090,"localhost","/",response -> {
          response.handler(responseBody -> {
            testContext.assertTrue(responseBody.toString().contains("welcome"));
            async.complete();
          });
        });
  }



}

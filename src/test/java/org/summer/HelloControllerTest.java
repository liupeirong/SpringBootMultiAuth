package org.summer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.summer.controller.HelloController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest {
  @Autowired
  private HelloController helloController;

  @WithAnonymousUser
  @Test(expected = AccessDeniedException.class)
  public void givenUnauthenticated_whenCallHelloController_thenThrowException(){
    helloController.calc(1,2);
  }

  @WithMockUser(username="admin", roles={"ADMIN"})
  @Test
  public void givenAuthenticated_whenCallHelloController_thenOk() {
    Assert.assertEquals(helloController.calc(1,2).getBody().getAnswer(), 3);
  }
}
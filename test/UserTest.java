import org.junit.*;

import java.util.*;

import play.Logger;
import play.test.*;
import models.*;

public class UserTest extends UnitTest 
{
  private User u1,u2,u3;
  
  @Before
  public void setup()
  {
    u1 = new User(true, "Homer","Simpson","homer@simpson.com","secret");
    u2 = new User(true, "Lisa","Simpson","lisa@simpson.com","secret");
    u3 = new User(false, "Maggie","Simpson","maggie@simpson.com","secret");
  
    u1.save();
    u2.save();
    u3.save();
  }
  
  @After
  public void teardown()
  {
    u1.delete();
    u2.delete();
    u3.delete();
  }

  @Test
  public void testCreate()
  {
    User a = User.findByEmail("homer@simpson.com");
    assertNotNull(a);
    assertEquals("Homer", a.firstName);
    assertEquals("Simpson",a.lastName);
   
    User b = User.findByEmail("lisa@simpson.com");
    assertNotNull(b);
    assertEquals("Lisa", b.firstName);
    assertEquals("Simpson",b.lastName);
  }
  
  @Test
  public void testNotThere()
  {
    User a = User.findByEmail("gorgeous@george.com");
    assertNull(a);
  }
  
}
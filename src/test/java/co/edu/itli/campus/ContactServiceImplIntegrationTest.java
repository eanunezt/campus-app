package co.edu.itli.campus;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.edu.itli.campus.register.model.Programa;
import co.edu.itli.campus.register.services.jpa.ProgramService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class ContactServiceImplIntegrationTest {
 
  
 /*
    @Autowired
    private ContactService contactService;*/
    
    @Autowired
    private ProgramService programService;
   
 
    // write test cases here
    
    @Test
    public void testProgramId1Exist() {
        String name = "alex";
        Programa found = programService.findById(1l);
      
        assertNotNull(found);
        
     }
}
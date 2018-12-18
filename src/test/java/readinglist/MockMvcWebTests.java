package readinglist;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import readinglist.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class MockMvcWebTests {

  @Autowired
  WebApplicationContext webContext;
  private MockMvc mockMvc;
/*
  @Before
  public void setupMockMvc() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webContext)
        .build();
  }
  
  @Test
  public void redirectFromRoot() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "/readingList"));
  }
*/
  @Before
  public void setupMockMvc() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webContext)
        .apply(springSecurity())
        .build();
  }
  
  @Test
  public void homePage_unauthenticatedUser() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "http://localhost/login"));
  }
  
  @Test
  public void homePage() throws Exception {
    mockMvc.perform(get("/readingList"))
        .andExpect(status().isOk())
        .andExpect(view().name("readingList"))
        .andExpect(model().attributeExists("books"))
        .andExpect(model().attribute("books", is(empty())));
  }
  
  @Test
  public void postBook() throws Exception {
    mockMvc.perform(post("/readingList")
           .contentType(APPLICATION_FORM_URLENCODED)
           .param("title", "BOOK TITLE")
           .param("author", "BOOK AUTHOR")
           .param("isbn", "1234567890")
           .param("description", "DESCRIPTION"))
           .andExpect(status().is3xxRedirection())
           .andExpect(header().string("Location", "/readingList"));
    
    Book expectedBook = new Book();
    
    Reader expectedReader = new Reader();
    expectedReader.setUsername("user1");//("craig");
    expectedReader.setPassword("user1Pass");//("password");
    //expectedReader.setFullname("Craig Walls");
    
    expectedBook.setId(1L);
    expectedBook.setReader(expectedReader);
    expectedBook.setTitle("BOOK TITLE");
    expectedBook.setAuthor("BOOK AUTHOR");
    expectedBook.setIsbn("1234567890");
    expectedBook.setDescription("DESCRIPTION");
    
    mockMvc.perform(get("/readingList"))
           .andExpect(status().isOk())
           .andExpect(view().name("readingList"))
           .andExpect(model().attributeExists("books"))
           .andExpect(model().attribute("books", hasSize(1)))
           .andExpect(model().attribute("books", 
                        contains(samePropertyValuesAs(expectedBook))));
  }

}

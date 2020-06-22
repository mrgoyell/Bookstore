# BookStore

A basic bookstore management system. 

##Developement
### Languages & Frameworks
The application has been developed in ***java*** using **Spring Boot** framework. Some *Rest APIs* are manually created whereas others are exposed using the ***Spring Data Rest*** library. By default the application has been exposed to **Port 8080**. This can be modified from the ***application.properties***

###Database
To connect the database with spring boot *JDBC connector* is used and repositories are extended from ***Spring Data JPA***. The application supports the use of 2 separate databases via switching profile:
* **H2** : This is an in memory database and any new data stored will be cleared at the end of execution. 
           Database can be set be setting the *active-profile to **test***. Its properties can be modified from ***application-test.properties***. Default entries in the database have been added in ***Data.sql*** it can be modified.**(Note: Modifying this file might affect some repository unit tests created)**

* **MYSQL**: This can connect to the local database created on *MYSQL*. By default it expects a database by the name of **Bookstore** on **Port 3306**. This can be changed from ***application-mysql.properties***

##Entities
### Book
* Isbn ~ String ~ ID
* Title ~ String ~ Not Empty
* Author ~ String ~ Not Empty
* Price ~ Float ~ Not Null
* Quantity ~ Integer ~ Default:1

##API Services
* ###Add Book
    Adds Book to the bookstore      
    * **Endpoint**: `POST /books`
    * **Request Body** (sample):   
    
        <code>
        {
                "isbn": "fef",
                "title": "reprehenderit",    
                "author": "ddd ",
                "price": 22.3,
                "quantity": 2       **(Optional)**
        }
        </code>
   * **Success Response:**
   
     * **Code:** 201 CREATED <br />
       **Content**(sample):
       
       <code>
       {
           "isbn": "fef",
           "title": "reprehenderit",
           "author": "ddd ",
           "price": 22.3,
           "quantity": 2
       }
       </code>
    * **Error Response:**
    
      * **Code:** 400 BAD REQUEST<br />
        **Content**(sample):
        
        <code>{
                  "timestamp": "2020-06-22T08:15:16.934+00:00",
                  "status": 400,
                  "error": "Bad Request",
                  "message": "",
                  "path": "/books/"
              }</code>
           
* ###Search Book 
    Search a book in the bookstore by `isbn`,`title`(partial matching),`author`(partial matching)    
    * Endpoint: `GET /books/search`
    * Params: &search
    
* ###Get Media Coverage
    Searches a book by `isbn` & finds its media coverage from [TypiCode](http://jsonplaceholder.typicode.com/posts) and returns all the titles of the media coverages found
    * **Endpoint**: `GET /books/{isbn}/getMediaCoverage`
    * **Success Response:**
    
      * **Code:** 200 <br />
        **Content:** 
    
            [
                "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "qui est esse",
                "dolorem eum magni eos aperiam quia",
                "eveniet quod temporibus"
            ]
     
    * **Error Response:**
    
      * **Code:** 404 NOT FOUND <br />
    
      OR
    
      * **Code:** 204 NO CONTENT <br />
      
* ###Buy Book
    Buys a copy of the book (if exists) from the bookstore. It will add another copy to the bookstore if last copy of the book was sold.
    * **Endpoint**: `GET /books/{isbn}/buy`
    * **Success Response:**
    
      * **Code:** 202 ACCEPTED <br />
        **Content:** <code>"Purchased Successfully"</code>

##Deployment
The application can be run as a docker container. Maintained docker repository can be found at `mrgoyell/bookstore`
Currently the following tags are supported:
* **latest**: Will pull the latest image on the master branch.

###Execution
After installing docker on your system/server. The image can be pulled by:

<code>docker pull mrgoyell/bookstore:<supported-tag></code>

After successfully pulling the image you can run the application by:

<code>sudo docker run -p 8080:8080 -d -t bookstore:<supported-tag></code>
 


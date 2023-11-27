<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1">
    <title>Google Books Embedded Viewer API Example</title>
    <script type="text/javascript" src="https://www.google.com/books/jsapi.js"></script>
    <script type="text/javascript">
    /**
     * This function is the call-back function for the JSON scripts which
     * executes a Google book search response.
     *
     * @param {JSON} booksInfo is the JSON object pulled from the Google books service.
     */
    function listEntries(booksInfo) {
      // Clear any old data to prepare to display the Loading... message.
      var div = document.getElementById("data");
      if (div.firstChild) div.removeChild(div.firstChild);

      var mainDiv = document.createElement("div");

      for (i in booksInfo) {
        // Create a DIV for each book
        var book = booksInfo[i];
        var thumbnailDiv = document.createElement("div");
        thumbnailDiv.className = "thumbnail";

        // Add a link to each book's informtaion page
        var a = document.createElement("a");
        a.href = book.info_url;
        a.innerHTML = book.bib_key;

        // Display a thumbnail of the book's cover
        var img = document.createElement("img");
        img.src = book.thumbnail_url;
        a.appendChild(img);

        thumbnailDiv.appendChild(a);

        // Alert the user that the book is not previewable
        var p = document.createElement("p");
        p.innerHTML = book.preview;
        if (p.innerHTML == "noview"){
          p.style.fontWeight = "bold";
          p.style.color = "#f00";
        }

        thumbnailDiv.appendChild(p);
        mainDiv.appendChild(thumbnailDiv);
      }
      div.appendChild(mainDiv);
    }
     
     /**
     *
     * @param {DOM object} query The form element containing the
     *                     input parameters "isbns"
     */
    function search(query) {
      // Clear any old data to prepare to display the Loading... message.
      var div = document.getElementById("data");
      if (div.firstChild) div.removeChild(div.firstChild);

      // Show a "Loading..." indicator.
      var div = document.getElementById('data');
      var p = document.createElement('p');
      p.appendChild(document.createTextNode('Loading...'));
      div.appendChild(p);

      // Delete any previous Google Booksearch JSON queries.
      var jsonScript = document.getElementById("jsonScript");
      if (jsonScript) {
        jsonScript.parentNode.removeChild(jsonScript);
      }

      // Add a script element with the src as the user's Google Booksearch query.
      // JSON output is specified by including the alt=json-in-script argument
      // and the callback funtion is also specified as a URI argument.
      var scriptElement = document.createElement("script");
      scriptElement.setAttribute("id", "jsonScript");
      scriptElement.setAttribute("src",
          "https://books.google.com/books?bibkeys=" +
          escape(query.isbns.value) + "&jscmd=viewapi&callback=listEntries");
      scriptElement.setAttribute("type", "text/javascript");
      // make the request to Google booksearch
      document.documentElement.firstChild.appendChild(scriptElement);
    }
     
      
    </script>
  </head>
  <body>
  	<h2 id="interactive-ajax" data-text="Interactive AJAX">Interactive AJAX</h2>
    <form onSubmit="return false">
      <p>Google Viewability API Query:
        <input type="text" name="isbns"
          value="0064430073, 0064430065, 0606018034, 0606011234" />
        <button id="search_button"
          onClick="search(this.form)">Search</button>
      </p>
    </form>
    <div id="data" style="margin-left: 5em;"></div>
    
  </body>
</html>




    
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit book</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/buttons/submit-button.css">
</head>
<body>

<nav id="navUl">
    <ul>
        <li><a href="login.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <li><a href="new_book.jsp">Create book</a></li>
        <li><a href="new_librarian.jsp">Create Librarian</a></li>
        <li><a href="front?command=show_librarians">Librarians</a></li>
        <li><a href="front?command=show_users">Users</a></li>
        <li style="float:right"><a href="front?command=logout">Log Out</a></li>
        <li style="float:right" ><a href="front?command=profile">Profile</a></li>
    </ul>
</nav>

<h1 style="text-align: center">Edit book data</h1>
<div id="form-container">
    <form name="newBookForm" action="front?formerIsbn=${param.formerIsbn}" method="post">

        <input type="hidden" name="command" value="edit_book">

        <h2>Title</h2>
        <div class="input-div">
            <label>
                <input class="input" name="title" type="text"/>
            </label>
        </div>

        <h2>Author</h2>
        <div class="input-div">
            <label>
                <input class="input" name="author" type="text"/>
            </label>
        </div>

        <h2>ISBN</h2>
        <div class="input-div">
            <label>
                <input class="input" name="isbn" type="text"/>
            </label>
        </div>

        <h2>Number of copies</h2>
        <div class="input-div">
            <label>
                <input class="input" name="copies_number" type="text"/>
            </label>
        </div>

        <h2>Date of publication</h2>
        <div class="input-div">
            <label>
                <input class="input" name="date" type="date"/>
            </label>
        </div>

        <h2>Publisher</h2>
        <div class="input-div">
            <label>
                <input class="input" name="publisher" type="text">
            </label>
        </div>

        <div class="input-div"  style="padding: 20px">
            <input class="submit-button" type="submit" value="Confirm changes">
        </div>
    </form>
</div>

</body>
</html>

<style>
    <%--  Form styles  --%>
    #form-container {
        display: block;
        margin: auto 50px;
    }

    .input-div {
        padding-left: 20px;
    }

    .input {
        font-size: x-large;
    }
    * {
        font-family: 'Raleway', sans-serif;
    }
</style>

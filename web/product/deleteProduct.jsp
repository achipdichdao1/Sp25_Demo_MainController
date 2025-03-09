<h2>Delete Product</h2>
<p>Are you sure you want to delete this product?</p>
<form action="products?action=delete" method="post">
    <input type="hidden" name="id" value="<%= request.getParameter("id")%>">
    <input type="submit" value="Delete">
</form>

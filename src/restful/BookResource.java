package restful;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.BookStoreRemote;
import model.Books;

@Path("/book")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class BookResource {

	@EJB
	private BookStoreRemote bEjb;
	@Context
	private UriInfo uriInfo;

	@GET
	@Path("{id}")
	public Response getBook(@PathParam("id") String id) {
		Books book = bEjb.getBookById(id);

		if (book == null) {
			throw new NotFoundException();
		}

		return Response.ok(book).build();

	}

	@GET
	public Response getBooks() {
		ArrayList<Books> books = bEjb.getProducts();

		// work with GenericEntity and not BooksList in order json to work
		// properly
		GenericEntity<List<Books>> ent = new GenericEntity<List<Books>>(books) {
		};
		// BooksList bs = new BooksList(books);
		return Response.ok(ent).build();

	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createBook(Books b) {
		
		if (b == null) {
			throw new BadRequestException();
		}
		
		Integer bId = bEjb.insertBook(b);
		
		URI bookUri = uriInfo.getAbsolutePathBuilder().path(bId.toString()).build();
		return Response.created(bookUri).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteBook(@PathParam("id") String id) {
		
		bEjb.delBook(Integer.parseInt(id));
		
		return Response.noContent().build();
		
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	// @PathParam id can be directly casted to Integer and not String!
	public Response modifyBook(@PathParam("id") String id, Books bs) {
		
		if (bs == null) {
			throw new BadRequestException();
		}
		
		bEjb.modBook(bs, Integer.parseInt(id));
		return Response.ok(bs).build();
	}

}
package connector;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UniqueItem;
import dao.BookStoreRemote;
import model.Books;
import model.Order;
import model.User;
import userSpecific.Cart;

/**
 * Servlet implementation class Connector
 */
@WebServlet("/Connector")
public class Connector extends HttpServlet {

	@EJB
	private BookStoreRemote bs;

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String action = (String) request.getParameter("act");

		HttpSession ses = request.getSession();
		
		// old session has expired ! go to error page
		if (ses.isNew()) {
			response.sendRedirect("error.jsp?error=SessionTimeout");
			return;
		}

		if (action.equals("relBook")) { // clicked on relevant books operation,
										// remove attributes regarding previous
										// iteminfo attributes
			ses.removeAttribute("Copies");
			ses.removeAttribute("bookInfo");
			ses.removeAttribute("relBooks");

			String itId = request.getParameter("bId");

			Books b1 = bs.getBookById(itId);
			ArrayList<Books> bRel = bs.getRelevantBooks(itId, b1.getFamily());
			ses.setAttribute("bookInfo", b1);
			ses.setAttribute("relBooks", bRel);
			ses.setAttribute("Copies", "1");
			request.getRequestDispatcher("/itemInfo.jsp").forward(request, response);
			return;

		} else if (action.equalsIgnoreCase("itemInf")) {
			String itId = request.getParameter("bId");

			Books b1 = bs.getBookById(itId);
			ArrayList<Books> bRel = bs.getRelevantBooks(itId, b1.getFamily());
			ses.setAttribute("bookInfo", b1);
			ses.setAttribute("relBooks", bRel);
			ses.setAttribute("Copies", "1");
			request.getRequestDispatcher("/itemInfo.jsp").forward(request, response);
			return;
			
		} else if (action.equals("buyItems")) {
			Cart car = (Cart) ses.getAttribute("Cart");
			ArrayList<UniqueItem> userItems = car.getItems(); // arrayList with
																// objects
																// existing to
																// cart

			Enumeration<String> names = request.getParameterNames();
			boolean existing = false;
			while (names.hasMoreElements()) {
				String nam = names.nextElement();
				if (!nam.equals("act")) { // escape first parameter happens to
											// be act
					String quan = request.getParameter(nam); // quantity of the
																// specific book
					int copies, productId;
					try {
						productId = Integer.parseInt(nam.trim()); // products id
																	// checked
					} catch (NumberFormatException nfe) {
						productId = 1;
					}

					try {
						copies = Integer.parseInt(quan.trim());
					} catch (NumberFormatException nfe) {
						copies = 1;
					}
					if (copies != 0) { // quantity entered by user != 0
						for (int i = 0; i < userItems.size(); i++) {
							UniqueItem obj = userItems.get(i);
							if (obj.getObjId() == productId) { // product
																// already
																// exists in
																// users cart,
																// copies update
																// needed
								existing = true;
								obj.incrementObject(copies);
								if (obj.getCopies() == 0) { // 0 copies remove
															// from cart
									userItems.remove(obj);
								}
								break;
							}
						}
						if (!existing && copies > 0) { // product missing from
														// users cart
							@SuppressWarnings("unchecked")
							ArrayList<Books> b = (ArrayList<Books>) ses.getAttribute("booksList");
							Books bCurrent = new Books();
							for (int i = 0; i < b.size(); i++) {
								if (b.get(i).getId() == productId) {
									bCurrent = b.get(i);
								}
							}
							UniqueItem ui = new UniqueItem(productId, bCurrent, copies);
							car.addToCart(ui);
						}
					}
				}
			}
			ses.setAttribute("Cart", car);
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
			return;
			
		} else if (action.equals("remove")) {
			Cart car = (Cart) ses.getAttribute("Cart");
			car.removeItem(Integer.parseInt(request.getParameter("it")));
			ses.setAttribute("Cart", car);
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
			return;
			
		} else if (action.equals("clear")) {
			Cart car = (Cart) ses.getAttribute("Cart");
			car.emptyCart();
			ses.setAttribute("Cart", car);
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
			return;
			
		} else if (action.equals("incI")) { // better support with javascript
			String c = (String) ses.getAttribute("Copies");
			int cop;
			try {
				cop = Integer.parseInt(c.trim()) + 1;
			} catch (NumberFormatException nfe) {
				cop = 1;
			}
			ses.setAttribute("Copies", Integer.toString(cop));
			request.getRequestDispatcher("/itemInfo.jsp").forward(request, response);
			return;
			
		} else if (action.equals("decI")) { // better support with javascript
			String c = (String) ses.getAttribute("Copies");
			int cop;
			try {
				cop = Integer.parseInt(c.trim()) - 1;
				if (cop < 0) {
					cop = 0;
				}
			} catch (NumberFormatException nfe) {
				cop = 1;
			}
			ses.setAttribute("Copies", Integer.toString(cop));
			request.getRequestDispatcher("/itemInfo.jsp").forward(request, response);
			return;
			
		} else if (action.equals("doOrder")) {
			ArrayList<Books> productBooks = new ArrayList<Books>();
			Cart car = (Cart) ses.getAttribute("Cart");
			ArrayList<UniqueItem> userItems = car.getItems(); // arrayList with
																// objects
																// existing to
																// cart

			int userId = (Integer) ses.getAttribute("UsersId");
			for (int i = 0; i < car.getItems().size(); i++) {
				productBooks.add(userItems.get(i).getB());
			}

			bs.createOrder(userId, productBooks);
			car.getItems().clear(); // clear cart (ready for new transaction)
			ses.setAttribute("Cart", car);
			request.getRequestDispatcher("/checkout.jsp").forward(request, response);
			return;
			
		} else if (action.equals("logOff")) {
			ses.invalidate();
			response.sendRedirect("index.jsp"); // new request from
												// client to access
												// index.jsp so
												// session info
												// lost! (as
												// required)
			return;
			
		} else if (action.equals("doShowUsers")) {
			ArrayList<User> users = (ArrayList<User>) bs.getUsers();
			ses.setAttribute("Users", users);
			request.getRequestDispatcher("/users.jsp").forward(request, response);
			return;
			
		} else if (action.equals("doShowOrders")) { // orders presented to
													// administrator
			out.println("Show orders");
			ArrayList<Order> allOrders;
			allOrders = bs.showAllOrders();
			ses.setAttribute("AllOrders", allOrders);
			request.getRequestDispatcher("/userOrders.jsp").forward(request, response);
			return;
			
		} else if (action.equals("doInsertBook")) {
			String title = request.getParameter("booksTitle");
			String descr = request.getParameter("booksDescr");
			String price = request.getParameter("booksPrice");
			String family = request.getParameter("booksFam");
			String icoExt = request.getParameter("booksIcoExt");
			String icoName = request.getParameter("booksIcoName");
			String message = "";

			Books bN = new Books(title, descr, Double.parseDouble(price), icoName, icoExt, family);

			if (title.equals("") || descr.equals("") || price.equals("") || family.equals("") || icoExt.equals("")
					|| icoName.equals("")) {
				message = "Missing fields";
				request.setAttribute("bookBean", bN);
			} else {
				System.out.println(bs.insertBook(bN));
				message = "Book is successfully inserted";
			}
			request.setAttribute("bookMessage", message);
			request.getRequestDispatcher("/bookInsertion.jsp").forward(request, response);
			return;
			
		} else if (action.equals("orderHist")) { // older orders presented to
													// end user
			int userId = (Integer) ses.getAttribute("UsersId");
			ArrayList<Order> userOrders = bs.showUserOrders(userId);
			ses.setAttribute("UserOrders", userOrders);
			request.getRequestDispatcher("/userOrders.jsp").forward(request, response);
			return;
			
		} else if (action.equals("showCart")) { // forward through servlet (not jsp) so as to check session timeout!
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
			return;
			
		} else if (action.equals("showProducts")) {
			request.getRequestDispatcher("/products.jsp").forward(request, response);
			return;
			
		} else if (action.equals("showBookInsertion")) {
			request.getRequestDispatcher("/bookInsertion.jsp").forward(request, response);
			return;
			
		} else if (action.equals("showAdminOperations")) {
			request.getRequestDispatcher("/adminOperations.jsp").forward(request, response);
			return;
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getParameter("act"); // login, or
																// account
																// creation

		String message = "";
		String mail = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");

		if (mail == null) {
			mail = "";
		}
		if (password == null) {
			password = "";
		}
		if (password2 == null) {
			password2 = "";
		}

		User user1 = new User(mail, password);

		request.setAttribute("email", mail); // use a Bean type user instead of
												// unique fields!!
		request.setAttribute("password", password);
		request.setAttribute("password2", password2);

		if (password.equals("") || mail.equals("")) {
			message = "Missing fields!";
			user1.setMessage(message);
			request.setAttribute("message", message);
			request.setAttribute("userBean", user1);
			if (action.equals("doCreation")) {
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
			} else if (action.equals("doLogin")) {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
			}
			return;
		}

		if (action.equals("doLogin")) {

			if (!user1.validate()) { // insufficient info
				message = user1.getMessage();
				request.setAttribute("message", message);
				request.setAttribute("userBean", user1);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			int usersId = bs.login(mail, password);

			if (usersId != 0) {
				ArrayList<Books> prod = bs.getProducts();
				Cart cart = new Cart();
				HttpSession session = request.getSession();
//				session.setMaxInactiveInterval(20);
				session.setAttribute("booksList", prod);
				session.setAttribute("Cart", cart);
				session.setAttribute("UsersId", usersId);
				response.sendRedirect(request.getContextPath() + "/products.jsp");
			} else {
				message = "Wrong credentials!";
				user1.setMessage(message);
				request.setAttribute("message", message);
				request.setAttribute("userBean", user1);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}

		} else if (action.equals("doCreation")) {
			if (password2.equals("")) {
				message = "Missing fields!";
				user1.setMessage(message);
				request.setAttribute("message", message);
				request.setAttribute("userBean", user1);
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
				return;
			} else if (!password.equals(password2)) {
				message = "Different passwords entered";
				user1.setMessage(message);
				request.setAttribute("message", message);
				request.setAttribute("userBean", user1);
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
				return;
			}

			if (bs.login(mail, password) != 0) { // user already exists
				message = "You have already registered this account!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
			} else {
				int usersId = bs.createUser(mail, password);
				ArrayList<Books> prod = bs.getProducts();
				Cart cart = new Cart();
				HttpSession session = request.getSession();
				session.setAttribute("Cart", cart);
				session.setAttribute("booksList", prod);
				session.setAttribute("UsersId", usersId);
				request.getRequestDispatcher("/products.jsp").forward(request, response);
			}
		} else if (action.equals("doAdmin")) {
			if (password2.equals("")) {
				message = "Missing fields!";
				user1.setMessage(message);
				request.setAttribute("message", message);
				request.setAttribute("userBean", user1);
				request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
				return;
			} else if (!user1.validate()) { // insufficient info
				message = user1.getMessage();
				request.setAttribute("message", message);
				request.setAttribute("userBean", user1);
				request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
				return;
			}

			if (bs.adminLogin(mail, password)) {
				request.getRequestDispatcher("/adminOperations.jsp").forward(request, response);
			} else {
				message = "Wrong credentials!";
				user1.setMessage(message);
				request.setAttribute("userBean", user1);
				request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
			}
		}

	}

}

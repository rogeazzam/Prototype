package com.example.Prototype.client;

import com.example.Prototype.client.ocsf.AbstractServer;
import com.example.Prototype.client.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleServer extends AbstractServer {
	private static SessionFactory sessionFactory;

	public SimpleServer(int port) {
		super(port);
		sessionFactory=getSessionFactory();
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Movie.class);
		configuration.addAnnotatedClass(MovieList.class);
		configuration.addAnnotatedClass(Branch.class);
		configuration.addAnnotatedClass(BranchesList.class);
		configuration.addAnnotatedClass(Time.class);
		configuration.addAnnotatedClass(Person.class);
		configuration.addAnnotatedClass(Employee.class);
		configuration.addAnnotatedClass(NetworkManager.class);
		configuration.addAnnotatedClass(BranchManager.class);
		configuration.addAnnotatedClass(ContentManager.class);
		configuration.addAnnotatedClass(HomeWatch.class);
		configuration.addAnnotatedClass(Map.class);
		configuration.addAnnotatedClass(Hall.class);
		configuration.addAnnotatedClass(Ticket.class);
		configuration.addAnnotatedClass(CinemaTicket.class);
		configuration.addAnnotatedClass(HomeTicket.class);
		configuration.addAnnotatedClass(MultipleTickets.class);
		configuration.addAnnotatedClass(Costumer.class);
		configuration.addAnnotatedClass(Account.class);
		configuration.addAnnotatedClass(Seat.class);
		configuration.addAnnotatedClass(PurplePass.class);
		configuration.addAnnotatedClass(Report.class);
		configuration.addAnnotatedClass(TicketReport.class);
		configuration.addAnnotatedClass(RefundReport.class);
		configuration.addAnnotatedClass(otherpurchaseReport.class);
		configuration.addAnnotatedClass(ComplaintReport.class);
		configuration.addAnnotatedClass(MovieUpdate.class);
		ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static <T> List<T> getAll(Class<T> object,Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);

		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	public Time find(List<Time> times, int id){
		for(Time time:times){
			if(time.getId()==id)
				return time;
		}
		return null;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		System.out.println(msgString);
		Session newsession = sessionFactory.openSession();
		Transaction tx=null;
		try {
			tx=newsession.beginTransaction();
			if(msg.getClass().equals(Time.class)){
				List<Seat> seats=((Time)(msg)).getMap().getSeats();
				for(Seat seat:seats)
					newsession.update(seat);
				newsession.update((Time)msg);
				newsession.flush();
			}else if(msg.getClass().equals(Movie.class)){
				//newsession.delete((Movie)msg);
				newsession.update((Movie)msg);
				newsession.flush();
			}else if(msg.getClass().equals(Hall.class)){
				newsession.update((Hall)msg);
			}
			else if (msgString.startsWith("#showBranches")) {
				try {
					List<Branch> branches=getAll(Branch.class,newsession);
					BranchesList branchesList=new BranchesList();
					for(Branch branch:branches)
						branchesList.setBranch(branch);
					client.sendToClient(branchesList);
					System.out.format("Sent branches to client %s\n", client.getInetAddress().getHostAddress());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(msgString.startsWith("#showMovies")){
				try {
					ShowMovies(client,newsession);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (msgString.startsWith("#LOGIN ")) {
				String[] arrOfStr = msgString.split(" ");

				List<Person> people=getAll(Person.class,newsession);
				for(Person person:people) {
					if (person.getUserName().equals(arrOfStr[1].toString()) && person.getPassword().equals(arrOfStr[2].toString())) {
						if(person.isLoggedIn()){
							client.sendToClient("User Already Signed In!");
						}else {
							person.setLoggedIn(true);
							newsession.update(person);
							newsession.flush();
							client.sendToClient(person);
						}
					}
				}
			}

			else if(msgString.startsWith("#LogOut")){
				int id=Integer.parseInt(msgString.substring(7));
				Person person=(Person) newsession.load(Person.class, id);

				person.setLoggedIn(false);
				newsession.update(person);
				newsession.flush();
				client.sendToClient("LoggedOut");
			}

			else if(msgString.startsWith("#DeleteTime")){
				int id=Integer.parseInt(msgString.substring(11));
				Movie movie=(Movie)newsession.load(Movie.class,id);
				int timeId=movie.getScreeningTime().get(0).getId();
				System.out.println(timeId);
				Time time=(Time)newsession.load(Time.class,timeId);
				newsession.delete(timeId);
				newsession.flush();

			}else if(msgString.startsWith("#DeleteMovie")){
				int id=Integer.parseInt(msgString.substring(12));
				Movie movieToDelete=(Movie)newsession.load(Movie.class,id);
				List<Time> times=movieToDelete.getScreeningTime();
				for(Time time:times){
					time.setMovie(null);
					time.setHall(null);
					List<Ticket> tickets=time.getTickets();
					if(tickets != null) {
						for (Ticket ticket : tickets) {
							ticket.setCostumer(null);
							ticket.setDetails(null);
							Seat seat = ticket.getSeat();
							seat.setTicket(null);
							Map map = seat.getMap();
							map.setSeats(null);
							seat.setMap(null);
							ticket.setSeat(null);
							newsession.delete(seat);
							newsession.delete(map);
							newsession.delete(ticket);
						}
					}
					time.setTickets(null);
					newsession.delete(time);
				}
				movieToDelete.setScreeningTime(null);
				List<MovieList> movieToDeleteLists=movieToDelete.getLists();
				for(MovieList movieList:movieToDeleteLists){
					movieList.getMovies().remove(movieToDelete);
				}
				movieToDelete.setLists(null);
				newsession.delete(movieToDelete);
				newsession.flush();

				//ShowMovies(client, newsession);
				client.sendToClient(new goBack());

			}else if(msgString.startsWith("#HomeWatchList")){
				List<HomeWatch> homeWatch=getAll(HomeWatch.class, newsession);
				 //movies=new MovieList();
				MovieList movies=homeWatch.get(0).getMovies();
				List<Movie> list=movies.getMovies();
				for(Movie movie: list){
					System.out.println(movie.getName());
				}
				client.sendToClient(movies);
			}else if(msgString.startsWith("#AddMovie")){
				addMovie(client,msgString.substring(9),newsession);
			}else if(msgString.startsWith("#showBranchMovies")){
				int id=Integer.parseInt(msgString.substring(17));
				Branch branch=(Branch) newsession.load(Branch.class,id);
				MovieList movies=branch.getMovie();
				List<Movie> movieList=movies.getMovies();
				for(Movie movie:movieList){
					System.out.println(movie.getName());
				}
				client.sendToClient(movies);
			}
			else if(msg.getClass().equals(Seat.class)){
				newsession.update((Seat)msg);
				newsession.flush();
				client.sendToClient(new BranchesList());
			}

			else
				if(msgString.startsWith("#creditCardCheck")){
				String[] parts=(msgString.substring(18)).split("%%");
				int test=0;
				Account account=null;
				List<Account> accounts=getAll(Account.class, newsession);
				for(Account account1: accounts){
					if(account1.getAccountNum().equals(parts[0])){
						account=account1;
						test=1;
						break;
					}
				}
				if(test==0 || !account.isIdentical(parts[1],parts[2], parts[3])) {
					client.sendToClient("#creditCardError");
				}
				//System.out.println(parts[8]);
				else if(!account.isValidate(Double.valueOf(parts[8]))) {
					client.sendToClient("#noEnoughCredit");
				}
				else {
					account.debit(Double.valueOf(parts[8]));

					test = 0;  //costumer doesn't exist in databse.
					List<Costumer> costumers = getAll(Costumer.class, newsession);
					Costumer buyer = null;
					for (Costumer costumer : costumers) {
						if (costumer.getId().equals(parts[4]) && costumer.getFirst_name().equals(parts[5])
								&& costumer.getLast_name().equals(parts[6])) {
							buyer = costumer;
							buyer.setEmail(parts[7]);
							test = 1; //costumer found in database.
							break;
						}
					}
					if (buyer == null)
						buyer = new Costumer(parts[4], parts[5], parts[6], parts[7]);

					int next=10,j=0;
					boolean inCinema=false;
					MultipleTickets multipleTickets=new MultipleTickets();
					String Subject_toSend="Ticket Enterance Verification.";
					String text_toSend="";
					String[] parts2 = parts[9].split(",");
					String[] parts3 = parts[9].split("ss");
					int countTickets = 0;
					while(true) {
						if (parts.length == 10) {
							String[] parts4 = parts3[j++].split(",");
							inCinema=true;
							int len = parts4.length;
							Time time = (Time) newsession.load(Time.class, Integer.parseInt(parts4[len - 1]));

							for (int i = 0; i < len - 1; i++) {
								countTickets++;
								Ticket ticket = time.getMap().getSeats().get(Integer.parseInt(parts4[i])).getTicket();
								text_toSend += "Seat: " + ticket.getSeat().getName() + "  Hall: " + ticket.getDetails().getHall().getName()
														+ "  Branch: " + ticket.getDetails().getHall().getBranch().getName() + "\n";

								ticket.setCostumer(buyer);
								buyer.addTicket(ticket);
								ticket.setPurchase_account(account);
								account.addTicket(ticket);
								newsession.update(ticket);

								TicketReport ticketReport=new TicketReport(buyer.getFirst_name() + " " + buyer.getLast_name(),
													ticket.getDetails().getMovie().getName(), new Date(), ticket.getDetails().getHall().getBranch().getName(),
														ticket.getDetails().getHall().getName(), ticket.getSeat().getName(), ticket.getPrice());
								newsession.save(ticketReport);

								multipleTickets.addTicket((CinemaTicket) ticket);
							}
						}else
							break;

						if(parts3.length <= j) {
							if (countTickets == 20){
								Subject_toSend="Multiple Ticket Verification.";
								for(CinemaTicket ticket:multipleTickets.getTicketList()) {
									ticket.setMultiple_tickets(multipleTickets);
								}

								newsession.save(multipleTickets);
								for(CinemaTicket ticket:multipleTickets.getTicketList())
									newsession.update(ticket);
								otherpurchaseReport report=new otherpurchaseReport(buyer.getFirst_name() + " " +
												buyer.getLast_name(), "", new Date(), MultipleTickets.price);
								newsession.save(report);
							}
							break;
						}
					}
					if(!inCinema){
						int count=0;
						List<HomeTicket> homeTickets=getAll(HomeTicket.class, newsession);
						List<HomeTicket> available=new ArrayList<HomeTicket>();

						Subject_toSend="Home Ticket Details.";

						for(HomeTicket ticket: homeTickets){
							if(ticket.getCostumer()==null &&
									ticket.getDetails().getId() == Integer.parseInt(parts2[1])){
								text_toSend="Available Times: " + String.valueOf(ticket.getDetails().getDay()) + "/" +
												String.valueOf(ticket.getDetails().getMonth()) + "/" + String.valueOf(ticket.getDetails().getYear())
													+ "  At " + ticket.getDetails().getBegTime() + " Until " + ticket.getDetails().getEndTime();
								count++;
								available.add(ticket);
								if(count==Integer.parseInt(parts2[0]))
									break;
							}
						}

						if(count >= Integer.parseInt(parts2[0])) {
							for (HomeTicket ticket : available) {
								ticket.setCostumer(buyer);
								buyer.addTicket(ticket);
								ticket.setPurchase_account(account);
								account.addTicket(ticket);
								newsession.update(ticket);
								otherpurchaseReport report=new otherpurchaseReport(buyer.getFirst_name() + " " +
										buyer.getLast_name(), "", new Date(), ticket.getPrice());
								newsession.save(report);
							}
						}
					}


					if (test == 0)
						newsession.save(buyer);
					else
						newsession.update(buyer);
					newsession.update(account);
					newsession.flush();

					client.sendToClient("#found");

					buyer.sendMail(Subject_toSend, text_toSend);
				}

			}

				else if(msgString.startsWith("#CheckIfExists")) {
					String id=msgString.substring(14);
					List<Costumer> costumers=getAll(Costumer.class, newsession);

					for(Costumer costumer: costumers){
						if(costumer.getId().equals(id)) {
							String verfication_code=String.valueOf(ThreadLocalRandom.current().nextInt(1000,10000));
							Random random=new Random();
							costumer.sendMail("verification code"
									,verfication_code);
							costumer.setVerification_code(verfication_code);
							break;
						}
					}
				}
				else if(msgString.startsWith("#EnteredVerification")){
					String[] parts=msgString.split("%%");
					Costumer costumer=(Costumer) newsession.load(Costumer.class, parts[1]);

					if(costumer.getVerification_code().equals(parts[2])) {
						costumer.setVerification_code("");
						client.sendToClient(costumer);
					}else {
						costumer.setVerification_code("");
						client.sendToClient("#NotVerified");
					}
				}
				else if(msgString.startsWith("#ReturnTicket")){
					int id=Integer.parseInt(msgString.substring(13));

					Ticket ticketToDelete=(Ticket) newsession.load(Ticket.class,id);
					Costumer costumer=ticketToDelete.getCostumer();
					ticketToDelete.setCostumer(null);
					costumer.deleteTicket(ticketToDelete);
					Time time=ticketToDelete.getDetails();

					/*Checking time difference with current time */
					double diffHours=0;
					try {
						String day=String.valueOf(time.getDay());
						String month=String.valueOf(time.getMonth());

						String str=month + "-" + day
									+ "-" + String.valueOf(time.getYear()) +" "+time.getBegTime()+":"+"00";
						SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
								"MM-dd-yyyy HH:mm:ss");
						Date lFromDate1 = datetimeFormatter1.parse(str);
						Timestamp movieTime = new Timestamp(lFromDate1.getTime());

						Timestamp currentTime = new Timestamp(System.currentTimeMillis());

						diffHours=compareTwoTimeStamps(currentTime, movieTime);
					} catch(Exception e) { //this generic but you can control another types of exception
						// look the origin of excption
					}
					Account account=ticketToDelete.getPurchase_account();

					if(diffHours > 3.0 || (diffHours > 1.0 && ticketToDelete.getClass().equals(HomeTicket.class)))
						account.credit(ticketToDelete.getPrice());
					else if(diffHours > 1.0)
						account.credit(ticketToDelete.getPrice() * 0.5);
					/*			......			*/

					List<TicketReport> reports=getAll(TicketReport.class, newsession);
					TicketReport reportToDelete=null;
					for(TicketReport report:reports)
						if(report.getBranch().equals(ticketToDelete.getDetails().getHall().getBranch().getName())
								&& report.getHall().equals(ticketToDelete.getDetails().getHall().getName()) &&
									report.getSeat().equals(ticketToDelete.getSeat().getName())) {
							reportToDelete = report;
							break;
						}
					newsession.delete(reportToDelete);

					account.deleteTicket(ticketToDelete);
					ticketToDelete.setPurchase_account(null);
					Seat seat=ticketToDelete.getSeat();
					if(seat!=null) {
						seat.setStatus(0);
						newsession.update(seat);
					}

					newsession.update(costumer);
					newsession.update(account);
					newsession.update(ticketToDelete);
					newsession.flush();

					client.sendToClient(costumer);
				}
				else if(msgString.startsWith("#PurplePass")){
					String str=msgString.substring(11);
					SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
							"MM-dd-yyyy HH:mm:ss");
					Date lFromDate1 = datetimeFormatter1.parse(str);
					Timestamp ts = new Timestamp(lFromDate1.getTime());

					PurplePass purplePass=(PurplePass) newsession.load(PurplePass.class, 1);

					double movieTime=ts.getTime();
					double beg=-1,end=-1;
					if(purplePass.getBeg()!=null && purplePass.getEnd()!=null) {
						beg = purplePass.getBeg().getTime();
						end = purplePass.getEnd().getTime();
					}

					if(purplePass.isStatus() && movieTime >= beg && movieTime <= end)
						client.sendToClient(String.valueOf(purplePass.getMaxSeats()));
					else
						client.sendToClient(String.valueOf(-1));

				}
				else if(msg.getClass().equals(MultipleTickets.class)){
					MultipleTickets multipleTickets=(MultipleTickets) msg;

					List<CinemaTicket> tickets=multipleTickets.getTicketList();
					if(tickets==null)
						client.sendToClient("Choose more Tickets to continue...");
					else {
						newsession.save(multipleTickets);
					}
					newsession.flush();
				}
				else if(msgString.startsWith("#UpdatePurplePass")){
					String[] parts=msgString.split("%%");
					PurplePass purplePass=(PurplePass) newsession.load(PurplePass.class, 1);

					if(Integer.parseInt(parts[3]) == -1){
						purplePass.setStatus(false);
						purplePass.setMaxSeats(-1);
					}else {
						SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
								"MM-dd-yyyy HH:mm:ss");
						Date lFromDate1 = datetimeFormatter1.parse(parts[1]);
						Timestamp startTime = new Timestamp(lFromDate1.getTime());

						Date lFormDate2 = datetimeFormatter1.parse(parts[2]);
						Timestamp endTime = new Timestamp(lFormDate2.getTime());

						purplePass.setStatus(true);
						purplePass.setMaxSeats(Integer.parseInt(parts[3]));
						purplePass.setBeg(startTime);
						purplePass.setEnd(endTime);

						if(Integer.parseInt(parts[3]) == 0){
							List<Costumer> costumers=getAll(Costumer.class, newsession);

							for(Costumer costumer:costumers){
								boolean test=false;
								double returnMoney=0;
								Account account=null;
								List<Ticket> tickets=new ArrayList<Ticket>();
								for(Ticket ticket:costumer.getTickets())
									if(ticket.getClass().equals(CinemaTicket.class))
										tickets.add(ticket);
								System.out.println(tickets.size());
								for(Ticket ticket:tickets){
									if(ticket.getClass().equals(HomeTicket.class))
										continue;
									String str=String.valueOf(ticket.getDetails().getMonth()) + "-" +
											String.valueOf(ticket.getDetails().getDay()) + "-" + String.valueOf(ticket.getDetails().getYear())
												+ " " + "00:00:00";
									Date lFromDate3 = datetimeFormatter1.parse(str);
									Timestamp movieTime = new Timestamp(lFromDate3.getTime());

									double mTime= movieTime.getTime();
									double sTime=startTime.getTime();
									double eTime=endTime.getTime();
									if(mTime >= sTime && mTime <= eTime) {
										test = true;
										String branch_name=ticket.getDetails().getHall().getBranch().getName();
										returnMoney += ticket.getPrice();
										account = ticket.getPurchase_account();
										ticket.setCostumer(null);
										ticket.setPurchase_account(null);
										account.deleteTicket(ticket);
										account.credit(ticket.getPrice());
										ticket.getSeat().setStatus(0);
										costumer.deleteTicket(ticket);
										newsession.update(ticket.getSeat());
										newsession.update(ticket);
										newsession.update(costumer);
										newsession.update(account);

										RefundReport refundReport=new RefundReport(costumer.getFirst_name() + " " + costumer.getLast_name(),
												"", new Date(), returnMoney);
										refundReport.setBranch_name(branch_name);
										newsession.save(refundReport);
									}
								}
								if(test){
									newsession.flush();
									costumer.sendMail("Canceled Tickets due to Restrictions",
												String.valueOf(returnMoney) + " shekels returned back to the accounts");
								}
							}
						}
					}
					newsession.update(purplePass);
					newsession.flush();
				}else if(msgString.startsWith("#getReports")){
					List<Report> reports = getAll(Report.class, newsession);
					Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

					double currentTime = currentTimestamp.getTime();

					for(Report report:reports){
						double complaintTime = new Timestamp(report.getDate().getTime()).getTime();
						double diffHours = (currentTime - complaintTime) / (60 * 60 * 1000);

						if((int)(diffHours/24) < 0 && (int)(diffHours/24) >= 30)
							reports.remove(report);
						if(reports.size() == 0)
							break;
					}
					ReportList reportList = new ReportList(reports);
					client.sendToClient(reportList);
				}else if(msgString.startsWith("#sendComplaint")){
					String[] parts = msgString.split("%%");

					Costumer costumer = (Costumer) newsession.load(Costumer.class, parts[3]);
					ComplaintReport complaint = new ComplaintReport(costumer.getFirst_name() + " " + costumer.getLast_name(),
								new Date(), costumer, parts[1], parts[2]);
					complaint.setCreditCardNum(parts[4]);
					complaint.setValidityMonth(parts[5]);
					complaint.setValidityYear(parts[6]);
					complaint.setCvvNum(parts[7]);
					costumer.addComplaint(complaint);

					newsession.save(complaint);
					newsession.update(costumer);
					newsession.flush();
				}else if(msgString.startsWith("#getComplaints")){
					List<ComplaintReport> complaints=getAll(ComplaintReport.class, newsession);
					for(ComplaintReport complaint:complaints) {
						if (complaint.isAnswered())
							complaints.remove(complaint);
						if(complaints.size() == 0)
							break;
					}
					ComplaintReportList complaintReportList=new ComplaintReportList(complaints);

					client.sendToClient(complaintReportList);
				}else if(msgString.startsWith("#replyToComplaint")){
					String[] parts=msgString.split("%%");
					ComplaintReport complaint=(ComplaintReport) newsession.load(ComplaintReport.class, Integer.parseInt(parts[3]));
					Costumer costumer=complaint.getCostumer();

					complaint.setAnswered(true);

					costumer.sendMail("Reply: " + parts[2], parts[1]);
				}else if(msgString.startsWith("#InitilaizeVerCode")){
					String id=msgString.substring(18);

					Costumer costumer=(Costumer) newsession.load(Costumer.class, id);
					costumer.setVerification_code(null);
					newsession.update(costumer);
					newsession.flush();

					client.sendToClient("updated");
				}else if(msgString.startsWith("#UpdatePermission")){
					String[] parts=msgString.split("%%");
					int id=Integer.parseInt(parts[1]);
					double new_price=Double.valueOf(parts[2]);
					boolean found=false;

					List<MovieUpdate> list=getAll(MovieUpdate.class, newsession);
					for(MovieUpdate toUpdate:list)
						if(toUpdate.getTime_id() == id){
							found=true;
							toUpdate.setNew_price(new_price);
							newsession.update(toUpdate);
						}

					if(!found) {
						MovieUpdate toUpdate = new MovieUpdate(id, new_price);
						newsession.save(toUpdate);
					}
						newsession.flush();
				}
				else if(msgString.startsWith("#UpdateTicketsPrice")){
					String[] parts=msgString.split("%%");
					int id=Integer.parseInt(parts[1]);
					List<MovieUpdate> list=getAll(MovieUpdate.class, newsession);
					MovieUpdate movieToDelete=null;
					Time time=(Time) newsession.load(Time.class, id);
					double new_price=0;

					for(MovieUpdate movieUpdate:list)
						if(time.getId() == movieUpdate.getTime_id()) {
							new_price = movieUpdate.getNew_price();
							movieToDelete=movieUpdate;
							break;
						}

					List<Ticket> tickets=time.getTickets();

					for(Ticket ticket:tickets){
						ticket.setPrice(new_price);
						newsession.update(ticket);
					}
					newsession.delete(movieToDelete);
					newsession.flush();
				}else if(msgString.startsWith("#getMoviesToUpdate")){
					List<MovieUpdate> list=getAll(MovieUpdate.class, newsession);
					List<Time> list2=new ArrayList<Time>();

					for(MovieUpdate movieUpdate:list){
						Time time=(Time) newsession.find(Time.class, movieUpdate.getTime_id());
						list2.add(time);
					}

					client.sendToClient(list2);
				} else if (msgString.startsWith("#DisApproveUpdate")) {
					String[] parts=msgString.split("%%");
					int id=Integer.parseInt(parts[1]);
					List<MovieUpdate> list=getAll(MovieUpdate.class, newsession);
					Time time=(Time) newsession.load(Time.class, id);

					for(MovieUpdate movieUpdate:list)
						if(time.getId() == movieUpdate.getTime_id()) {
							newsession.delete(movieUpdate);
							newsession.flush();
							break;
						}
				}else if(msgString.startsWith("#ComplaintRefund")){
					String[] parts=msgString.split("%%");
					ComplaintReport complaint=(ComplaintReport) newsession.load(ComplaintReport.class, Integer.parseInt(parts[2]));

					Account account=(Account) newsession.load(Account.class, complaint.getCreditCardNum());
					account.credit(Double.valueOf(parts[1]));
				}

			tx.commit();
		} catch (Exception var10) {
			if (newsession != null) {
				newsession.getTransaction().rollback();
			}

			System.err.println("An error occured, changes have been rolled back.");
			var10.printStackTrace();
		} finally {
			if (newsession != null) {
				newsession.close();
			}

		}
	}

	public void addMovie(ConnectionToClient client, String str,Session newsession) throws Exception {
		String[] parts=str.split("%%");
		String[] date=parts[1].split("-");
		int day=Integer.parseInt(date[2]);
		int month=Integer.parseInt(date[1]);
		int year=Integer.parseInt(date[0]);
		Time time=new Time(day,month,year,parts[2],parts[3]);
		//Branch branch=(Branch) newsession.load(Branch.class,parts[4]);
		List<Hall> halls=getAll(Hall.class,newsession);
		Hall goal=null;

		for(Hall hall:halls){
			if(hall.getName().equals(parts[5]))
				goal=hall;
		}

		if(goal==null)
			client.sendToClient("Hall doesn't exist!");
		else if(!goal.getBranch().getName().equals(parts[4])) {
			client.sendToClient("hall doesn't exit in the given branch");
		}
		else {
			Movie newMovie = new Movie(parts[0], parts[6], parts[7], parts[8], parts[9]);
			Map map = new Map();
			MovieList movies = goal.getBranch().getMovie();

			time.setHall(goal);
			time.setMap(map);
			time.setMovie(newMovie);
			map.setTime(time);
			newMovie.setScreeningTime(time);
			movies.setMovies(newMovie);
			newMovie.setList(movies);
			goal.addScreeningTime(time);

			newsession.save(newMovie);
			newsession.update(goal);
			newsession.save(time);
			newsession.update(movies);
			newsession.flush();

			client.sendToClient("Movie added successfully!");
		}
	}

	public void ShowMovies(ConnectionToClient client, Session newsession) throws Exception {
		List<Movie> movies= SimpleServer.getAll(Movie.class,newsession);
		MovieList movieList=new MovieList();
		//movieList.setMovies(movies);
		for(Movie movie:movies) {
			movieList.setMovies(movie);
		}
		client.sendToClient(movieList);
		System.out.format("Sent movies to client %s\n", client.getInetAddress().getHostAddress());
	}

	public static double compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp movieTime)
	{
		double milliseconds1 = movieTime.getTime();
		double milliseconds2 = currentTime.getTime();

		double diff = milliseconds1 - milliseconds2;
		double diffHours = diff / (60 * 60 * 1000);

		return diffHours;
	}

}
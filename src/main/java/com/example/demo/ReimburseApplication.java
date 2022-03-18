package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


//Simulate some business 30%, Documentation 5% - (javadocs), User Interaction 10%, Unit Testing (40% coverage) 40%
//Presentation 5%, Data Persistence 5%, Logging 5%

//Goal: Build 2 separate web APIs using spring boot and deploy and orchestrate the applications using Docker and Docker Compose.

//API 1
//Request reimbursement API
	//As a Employee I can:
//Submit a reimbursement request
//View all of my reimbursements
	// As a Manager I can:
//View all reimbursements
//Approve/Deny/Reassign reimbursements

//API 2
//Email API
//Receive request to send emails to specified users.

//Containerize and deploy the parts necessary to run the entire system

//JUnit
//Spring Test
//Docker

/**
 * Using jSon
 * @RestController = spring bean that communicates in a data format (convention, jSon or xml, we use jSon).
 *     Create methods that handle certain requests and returns data (maybe) as jSon
 *  Will set up as restful as possible (Restful representational state transfer)
 *  @RequestMapping = tells Spring web application context how to map controllers to certain types of requests
 *        like project 0 with paths
 *  After @RequestMapping we make @GetMapping
 *  Shown in first way, different kinds of @GetMapping 's
 *  When using multiple GetMapping isntances, change name @GetMapping("{name}")
 *    Looking at second @GetMapping("{id}") in first way, we hard coded everything except id, change parameters...
 *  Create DTO for @PostMapping called CreatePostDTO... for this DTO, no real action, make constructors and getters and setters
 *  @PostMapping in way 1 mocks workflow, created it, saved in db, and return new Response entity (.created)
 * 		 here we added the @Value("${server.port}") and the int port;
 *  @RequestBody inside @PostMapping in Way 1 = send in that CreatePostDto
 *    got to insomnia. new folder "name", new request post with json body.
 *    "posterName": "poster.name", enter "receiverName": "receiver.name", "content": "Tag you're it"
 * 	   go to headers, should have created a url with some-id at end
 *  @PutMapping or @PatchMapping does UPDATE
 * @PostMapping ADD NEW
 * @DeleteMapping
 * @Column for column mapping, can comment out @Column b/c hibernate will know theres a column there if private String column_name; is there.
 * 		@Transient on column will tell Hibernate to ignore the column
 *Optional used for null checks. (DataJPA, not hibernate)is
 *
 *
 * WORKER
 * state that the company values their morality and transparency. we require that all receipts be saved
 * 		and turned in to be reviewed. the "other" section is for things such as tipping when
 * 		eating out, buying resources for functions (i.e. buying water for colleagues at an event) and not
 * 		for things such as snacks and eating out at highly-priced restaurants for personal satisfaction.
 * request by posting his reimbursement per thing
 * MANAGER
 * use post a user example to help with posting approval/ denial... how to reassign?
 * If request comes in >$500, reassign
 *  else if (gas == 0 && food == 0 && tools == 0 && travel == 0 &&  hotel == 0)
 *  	deny
 * 	else everything except other : approve
 * 	Each repository needs @Autowire
 */


@SpringBootApplication
public class ReimburseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReimburseApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}
}














//
//	@Bean
//	RoleModel roleGrab = new RoleModel();
//
//	if(EmployeesDB.getRoles() == roleGrab.getRoles(WORKER);
//
//	{
//		@Bean
//		EmployeesDB worker = new EmployeesDB();
//		worker.get	W
//	}
//
//	else if(user.getRole == RoleModel.MANAGER)
//
//	{
//
//
//	}
//
//	else if(user.getRole ==RoleModel.REIMBURSEMENT_MANAGER){
//
//
//	}
//
//	else{
//		System.out.println("Please try to log in again with an existing username and password.");
//
//
//	};
//
//
//}


//
//
//	//post a user
//	@Bean
//	public CommandLineRunner postSomeone() {
//
//		return args -> {
//
//			EmployeesDB makeUser = new EmployeesDB();
//			makeUser.setEmployeeName("new.worker");
//			makeUser.setEmployeeEmail("hardlyworking@reimapi.com");
//			makeUser.setPassWord("w0rk");
//			makeUser.setRoles(RoleModel.WORKER);
//
//			ExpensesDB makeExpenses = new ExpensesDB();
//			makeExpenses.setExpense_date("1/31-02/04");
//			makeExpenses.setGas("85");
//			makeExpenses.setFood("58");
//			makeExpenses.setTools("5");
//			makeExpenses.setTravel("4");
//			makeExpenses.setHotel("65");
//			makeExpenses.setOther("2");
//
//			ReimbursementsDB makeReimbursements = new ReimbursementsDB();
//			makeReimbursements.setDates(" FEB ");
//			makeReimbursements.setStatus("APPROVED");
//			makeReimbursements.setAmount(500);
//		};
//	}
//}
//	/**
//	 * not an insomnia post
//	 * making a "post" in august's app. august posts on  johns page
//	 * @return
//	 */
////	@Bean
////	public CommandLineRunner postSomeone() {
////		return args -> {
////	Optional<MmUser> august = userRepository.findByIdd(3);
////	Optional<MmUser> august = userRepository.findByIdd(3);
////	if(august.isPresent() && john.isPresent()){
////
////			MmUser august = userRepository.getByID(3);
////			MmUser john = userRepository.getByID(4);
////
////			Set<MmTag> tags = new LinkedHashSet<>();
////			tags.adAll(Arrays.asList(new MmTag(0, "content"), new MmTag(o, "greeting")) );
////
////			MmPost post = new MmPost();
////			post.setPoster(august);
////			post.setReceiver(john);
////			post.setTags(tags);
////			post.setContent("Hello, John!");
////
////			postRepository.save(post);
////			}
////
////		};
////	}

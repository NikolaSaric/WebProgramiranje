Vue.component("login", {
	data: function () {
		    return {
		      username: "",
		      password: "",
		      registerDiv: false,
		      newUser: {}
		    }
	},
	template: ` 
<div>
	<navbar></navbar>
	<h2> Login</h2>
	<table>
		<tr>
			<td><b>Username: </b></td>
			<td> <input type="text" v-model="username" ></td>
		</tr>
		<tr>
			<td><b>Passsword: </b></td>
			<td> <input type="text" v-model="password" ></td>
		</tr>
		<tr>
            <td>  </td>
            <td><button v-on:click="login()">Login</button> </td>      
        </tr>
        <br>
        <tr>
        	<div v-if="registerDiv == false">
            <td>  </td>
            <td><button v-on:click="register()">Register</button> </td>
            </div>
        </tr>
	</table>
	
	<div v-if="registerDiv == true">
		<tr>
			<td><b>Username: </b></td>
			<td> <input type="text" v-model="newUser.username" ></td>
		</tr>
		<tr>
			<td><b>First Name: </b></td>
			<td> <input type="text" v-model="newUser.firstName" ></td>
		</tr>
		<tr>
			<td><b>Last Name: </b></td>
			<td> <input type="text" v-model="newUser.lastName" ></td>
		</tr>
		<tr>
			<td><b>Password: </b></td>
			<td> <input type="text" v-model="newUser.password" ></td>
		</tr>
		<tr>
			<td><b>Repeat Password: </b></td>
			<td> <input type="text" v-model="newUser.repeatPassword" ></td>
		</tr>
		<tr>
			<td><b>Phone Number: </b></td>
			<td> <input type="text" v-model="newUser.phoneNumber" ></td>
		</tr>
		<tr>
			<td><b>Email: </b></td>
			<td> <input type="text" v-model="newUser.email" ></td>
		</tr>
		<tr>
			<td><b>Picture: </b></td>
			<td> <input type="text" v-model="newUser.picture" ></td>
		</tr>
		<tr>
            <td>  </td>
            <td><button v-on:click="registerNewUser()">Register</button> </td>      
        </tr>
	</div>
	
	
</div>		  
`
	, 
	methods : {
		login : function () {
			if(this.username == "") {
				toast("Enter username.");
			}
			if(this.password == "") {
				toast("Enter password.");
			}
			
			axios
			.post('rest/server/login',{username: this.username, password: this.password})
			.then(response => {
				if(response.data !== null) {
					toast("Logged in as: " + response.data.username + response.data.role);
					localStorage.loggedUsername = response.data.username;
					localStorage.loggedFirstName = response.data.firstName;
					localStorage.loggedLastName = response.data.lastName;
					localStorage.loggedEmail = response.data.email;
					localStorage.loggedPhoneNumber = response.data.phoneNumber;
					localStorage.loggedBlocked = response.data.blocked;
					this.$router.push("/");
				} else {
					toast("Username or password are incorrect.");
					localStorage.setItem("loggedUser","");
				}
				
			});
		},
		register : function () {
			this.registerDiv = true;
		},
		registerNewUser: function() {
			if(this.newUser.username == "" || this.newUser.username == null) {
				toast("Enter username.");
				return;
			}
			if(this.newUser.firstName == "" || this.newUser.firstName == null) {
				toast("Enter first name.");
				return;
			}
			if(this.newUser.lastName == "" || this.newUser.lastName == null) {
				toast("Enter last name.");
				return;
			}
			if(this.newUser.email == "" || this.newUser.email == null) {
				toast("Enter email.");
				return;
			}
			if(this.newUser.phoneNumber == "" || this.newUser.phoneNumber == null) {
				toast("Enter phone number.");
				return;
			}
			if(this.newUser.password == "" || this.newUser.password == null) {
				toast("Enter password.");
				return;
			}
			if(this.newUser.repeatPassword == "" || this.newUser.repeatPassword == null) {
				toast("Repeat password.");
				return;
			}
			if(this.newUser.password != this.newUser.repeatPassword) {
				toast("Repeat password does not matches password.");
				return;
			}
			
			this.newUser.picture = "";
			
			axios
			.post('rest/server/register',this.newUser)
			.then(response => {
				toast(response.data);
				if(response.data.startsWith("Successfully")) {
					console.log("bravo");
					this.registerDiv = false;
				}
			});
		}
	},
});